/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.internal.release.osgi.commands;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.internal.executor.SwappedLogExecutor;
import com.liferay.portal.upgrade.internal.executor.UpgradeExecutor;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;
import com.liferay.portal.upgrade.internal.release.ReleaseManagerImpl;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=check", "osgi.command.function=checkAll",
		"osgi.command.function=execute", "osgi.command.function=executeAll",
		"osgi.command.function=list", "osgi.command.scope=upgrade"
	},
	service = ReleaseManagerOSGiCommands.class
)
public class ReleaseManagerOSGiCommands {

	@Descriptor("List pending upgrades")
	public String check() {
		return _check(false);
	}

	@Descriptor("List pending upgrade processes and their upgrade steps")
	public String checkAll() {
		return _check(true);
	}

	@Descriptor("Execute upgrade for a specific module")
	public String execute(String bundleSymbolicName) {
		List<UpgradeInfo> upgradeInfos = _releaseManagerImpl.getUpgradeInfos(
			bundleSymbolicName);

		if (upgradeInfos == null) {
			return "No upgrade processes registered for " + bundleSymbolicName;
		}

		try {
			_upgradeExecutor.execute(bundleSymbolicName, upgradeInfos, null);
		}
		catch (Throwable throwable) {
			_swappedLogExecutor.execute(
				bundleSymbolicName,
				() -> _log.error(
					"Failed upgrade process for module ".concat(
						bundleSymbolicName),
					throwable),
				null);
		}

		return null;
	}

	@Descriptor("Execute upgrade for a specific module and final version")
	public String execute(String bundleSymbolicName, String toVersionString) {
		List<UpgradeInfo> upgradeInfos = _releaseManagerImpl.getUpgradeInfos(
			bundleSymbolicName);

		if (upgradeInfos == null) {
			return "No upgrade processes registered for " + bundleSymbolicName;
		}

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			upgradeInfos);

		_upgradeExecutor.executeUpgradeInfos(
			bundleSymbolicName,
			releaseGraphManager.getUpgradeInfos(
				_releaseManagerImpl.getSchemaVersionString(bundleSymbolicName),
				toVersionString),
			null);

		return null;
	}

	@Descriptor("Execute all pending upgrades")
	public String executeAll() {
		Set<String> upgradeThrewExceptionBundleSymbolicNames = new HashSet<>();

		executeAll(upgradeThrewExceptionBundleSymbolicNames);

		if (upgradeThrewExceptionBundleSymbolicNames.isEmpty()) {
			return "All modules were successfully upgraded";
		}

		StringBundler sb = new StringBundler(
			(upgradeThrewExceptionBundleSymbolicNames.size() * 3) + 3);

		sb.append("The following modules had errors while upgrading:\n");

		for (String upgradeThrewExceptionBundleSymbolicName :
				upgradeThrewExceptionBundleSymbolicNames) {

			sb.append(StringPool.TAB);
			sb.append(upgradeThrewExceptionBundleSymbolicName);
			sb.append(StringPool.NEW_LINE);
		}

		sb.append("Use the command upgrade:list <module name> to get more ");
		sb.append("details about the status of a specific upgrade.");

		return sb.toString();
	}

	@Descriptor("List registered upgrade processes for all modules")
	public String list() {
		Set<String> bundleSymbolicNames =
			_releaseManagerImpl.getBundleSymbolicNames();

		StringBundler sb = new StringBundler(2 * bundleSymbolicNames.size());

		for (String bundleSymbolicName : bundleSymbolicNames) {
			sb.append(list(bundleSymbolicName));
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	@Descriptor("List registered upgrade processes for a specific module")
	public String list(String bundleSymbolicName) {
		List<UpgradeInfo> upgradeInfos = _releaseManagerImpl.getUpgradeInfos(
			bundleSymbolicName);

		StringBundler sb = new StringBundler(5 + (3 * upgradeInfos.size()));

		sb.append("Registered upgrade processes for ");
		sb.append(bundleSymbolicName);
		sb.append(StringPool.SPACE);
		sb.append(
			_releaseManagerImpl.getSchemaVersionString(bundleSymbolicName));
		sb.append(StringPool.NEW_LINE);

		for (UpgradeInfo upgradeProcess : upgradeInfos) {
			sb.append(StringPool.TAB);
			sb.append(upgradeProcess);
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected void executeAll(
		Set<String> upgradeThrewExceptionBundleSymbolicNames) {

		Set<String> upgradableBundleSymbolicNames =
			_releaseManagerImpl.getUpgradableBundleSymbolicNames();

		upgradableBundleSymbolicNames.removeAll(
			upgradeThrewExceptionBundleSymbolicNames);

		if (upgradableBundleSymbolicNames.isEmpty()) {
			return;
		}

		for (String upgradableBundleSymbolicName :
				upgradableBundleSymbolicNames) {

			try {
				List<UpgradeInfo> upgradeInfos =
					_releaseManagerImpl.getUpgradeInfos(
						upgradableBundleSymbolicName);

				_upgradeExecutor.execute(
					upgradableBundleSymbolicName, upgradeInfos, null);
			}
			catch (Throwable throwable) {
				_swappedLogExecutor.execute(
					upgradableBundleSymbolicName,
					() -> _log.error(
						"Failed upgrade process for module ".concat(
							upgradableBundleSymbolicName),
						throwable),
					null);

				upgradeThrewExceptionBundleSymbolicNames.add(
					upgradableBundleSymbolicName);
			}
		}

		executeAll(upgradeThrewExceptionBundleSymbolicNames);
	}

	private String _check(boolean showUpgradeSteps) {
		StringBundler sb = new StringBundler(3);

		sb.append(_checkPortal(showUpgradeSteps));

		if (sb.length() > 0) {
			sb.append(StringPool.NEW_LINE);
		}

		sb.append(_checkModules(showUpgradeSteps));

		return sb.toString();
	}

	private String _checkModules(boolean showUpgradeSteps) {
		StringBundler sb = new StringBundler();

		Set<String> bundleSymbolicNames =
			_releaseManagerImpl.getBundleSymbolicNames();

		for (String bundleSymbolicName : bundleSymbolicNames) {
			String schemaVersionString =
				_releaseManagerImpl.getSchemaVersionString(bundleSymbolicName);

			ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
				_releaseManagerImpl.getUpgradeInfos(bundleSymbolicName));

			List<List<UpgradeInfo>> upgradeInfosList =
				releaseGraphManager.getUpgradeInfosList(schemaVersionString);

			int size = upgradeInfosList.size();

			if (size > 1) {
				sb.append("There are ");
				sb.append(size);
				sb.append(" possible end nodes for ");
				sb.append(schemaVersionString);
				sb.append(StringPool.NEW_LINE);
			}

			if (size == 0) {
				continue;
			}

			List<UpgradeInfo> upgradeInfos = upgradeInfosList.get(0);

			UpgradeInfo lastUpgradeInfo = upgradeInfos.get(
				upgradeInfos.size() - 1);

			sb.append(
				_getModulePendingUpgradeMessage(
					bundleSymbolicName, schemaVersionString,
					lastUpgradeInfo.getToSchemaVersionString()));

			if (showUpgradeSteps) {
				sb.append(StringPool.COLON);

				for (UpgradeInfo upgradeInfo : upgradeInfos) {
					UpgradeStep upgradeStep = upgradeInfo.getUpgradeStep();

					sb.append(StringPool.NEW_LINE);
					sb.append(StringPool.TAB);
					sb.append(
						_getPendingUpgradeProcessMessage(
							upgradeStep.getClass(),
							upgradeInfo.getFromSchemaVersionString(),
							upgradeInfo.getToSchemaVersionString()));
				}
			}

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _checkPortal(boolean showUpgradeSteps) {
		try (Connection connection = DataAccess.getConnection()) {
			Version currentSchemaVersion =
				PortalUpgradeProcess.getCurrentSchemaVersion(connection);

			SortedMap<Version, UpgradeProcess> pendingUpgradeProcesses =
				PortalUpgradeProcess.getPendingUpgradeProcesses(
					currentSchemaVersion);

			if (!pendingUpgradeProcesses.isEmpty()) {
				Version latestSchemaVersion =
					PortalUpgradeProcess.getLatestSchemaVersion();

				StringBundler sb = new StringBundler();

				sb.append(
					_getModulePendingUpgradeMessage(
						"Portal", currentSchemaVersion.toString(),
						latestSchemaVersion.toString()));

				sb.append(" (requires upgrade tool or auto upgrade)");

				if (showUpgradeSteps) {
					sb.append(StringPool.COLON);

					for (SortedMap.Entry<Version, UpgradeProcess> entry :
							pendingUpgradeProcesses.entrySet()) {

						sb.append(StringPool.NEW_LINE);
						sb.append(StringPool.TAB);

						UpgradeProcess upgradeProcess = entry.getValue();
						Version version = entry.getKey();

						sb.append(
							_getPendingUpgradeProcessMessage(
								upgradeProcess.getClass(),
								currentSchemaVersion.toString(),
								version.toString()));

						sb.append(StringPool.NEW_LINE);

						currentSchemaVersion = version;
					}
				}

				return sb.toString();
			}
		}
		catch (SQLException sqlException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get pending upgrade information for the portal",
					sqlException);
			}
		}

		return StringPool.BLANK;
	}

	private String _getModulePendingUpgradeMessage(
		String moduleName, String currentSchemaVersion,
		String finalSchemaVersion) {

		return StringBundler.concat(
			"There are upgrade processes available for ", moduleName, " from ",
			currentSchemaVersion, " to ", finalSchemaVersion);
	}

	private String _getPendingUpgradeProcessMessage(
		Class<?> upgradeClass, String fromSchemaVersion,
		String toSchemaVersion) {

		StringBundler sb = new StringBundler(6);

		String toMessage = toSchemaVersion;

		if (UpgradeProcessUtil.isRequiredSchemaVersion(
				Version.parseVersion(fromSchemaVersion),
				Version.parseVersion(toSchemaVersion))) {

			toMessage += " (REQUIRED)";
		}

		sb.append(fromSchemaVersion);
		sb.append(" to ");
		sb.append(toMessage);
		sb.append(StringPool.COLON);
		sb.append(StringPool.SPACE);
		sb.append(upgradeClass.getName());

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseManagerOSGiCommands.class);

	@Reference
	private ReleaseManagerImpl _releaseManagerImpl;

	@Reference
	private SwappedLogExecutor _swappedLogExecutor;

	@Reference
	private UpgradeExecutor _upgradeExecutor;

}