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

package com.liferay.message.boards.service.persistence;

import com.liferay.message.boards.exception.NoSuchMessageException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the message-boards message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageUtil
 * @generated
 */
@ProviderType
public interface MBMessagePersistence
	extends BasePersistence<MBMessage>, CTPersistence<MBMessage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBMessageUtil} to access the message-boards message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the message-boards messages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid(String uuid);

	/**
	 * Returns a range of all the message-boards messages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where uuid = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByUuid_PrevAndNext(
			long messageId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of message-boards messages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message-boards messages
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the message-boards message where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByUUID_G(String uuid, long groupId)
		throws NoSuchMessageException;

	/**
	 * Returns the message-boards message where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the message-boards message where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the message-boards message where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message-boards message that was removed
	 */
	public MBMessage removeByUUID_G(String uuid, long groupId)
		throws NoSuchMessageException;

	/**
	 * Returns the number of message-boards messages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message-boards messages
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByUuid_C_PrevAndNext(
			long messageId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of message-boards messages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message-boards messages
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the message-boards messages where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByGroupId(long groupId);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByGroupId_PrevAndNext(
			long messageId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByGroupId_PrevAndNext(
			long messageId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of message-boards messages where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message-boards messages
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns all the message-boards messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the message-boards messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByCompanyId_PrevAndNext(
			long messageId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of message-boards messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching message-boards messages
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the message-boards messages where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUserId(long userId);

	/**
	 * Returns a range of all the message-boards messages where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByUserId_PrevAndNext(
			long messageId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of message-boards messages where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message-boards messages
	 */
	public int countByUserId(long userId);

	/**
	 * Returns all the message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadId(long threadId);

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadId(
		long threadId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByThreadId_First(
			long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByThreadId_First(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByThreadId_Last(
			long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByThreadId_Last(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByThreadId_PrevAndNext(
			long messageId, long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 */
	public void removeByThreadId(long threadId);

	/**
	 * Returns the number of message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages
	 */
	public int countByThreadId(long threadId);

	/**
	 * Returns all the message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadIdReplies(long threadId);

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadIdReplies(
		long threadId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadIdReplies(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByThreadIdReplies(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByThreadIdReplies_First(
			long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByThreadIdReplies_First(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByThreadIdReplies_Last(
			long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByThreadIdReplies_Last(
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByThreadIdReplies_PrevAndNext(
			long messageId, long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 */
	public void removeByThreadIdReplies(long threadId);

	/**
	 * Returns the number of message-boards messages where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages
	 */
	public int countByThreadIdReplies(long threadId);

	/**
	 * Returns all the message-boards messages where parentMessageId = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByParentMessageId(
		long parentMessageId);

	/**
	 * Returns a range of all the message-boards messages where parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByParentMessageId(
		long parentMessageId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByParentMessageId(
		long parentMessageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByParentMessageId(
		long parentMessageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where parentMessageId = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByParentMessageId_First(
			long parentMessageId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where parentMessageId = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByParentMessageId_First(
		long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where parentMessageId = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByParentMessageId_Last(
			long parentMessageId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where parentMessageId = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByParentMessageId_Last(
		long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where parentMessageId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByParentMessageId_PrevAndNext(
			long messageId, long parentMessageId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where parentMessageId = &#63; from the database.
	 *
	 * @param parentMessageId the parent message ID
	 */
	public void removeByParentMessageId(long parentMessageId);

	/**
	 * Returns the number of message-boards messages where parentMessageId = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @return the number of matching message-boards messages
	 */
	public int countByParentMessageId(long parentMessageId);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U(long groupId, long userId);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U(
		long groupId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_U_First(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_U_Last(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_U_PrevAndNext(
			long messageId, long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_U(long groupId, long userId);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_U(
		long groupId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_U_PrevAndNext(
			long messageId, long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public void removeByG_U(long groupId, long userId);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message-boards messages
	 */
	public int countByG_U(long groupId, long userId);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_U(long groupId, long userId);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C(long groupId, long categoryId);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C(
		long groupId, long categoryId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_First(
			long groupId, long categoryId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_Last(
			long groupId, long categoryId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_C_PrevAndNext(
			long messageId, long groupId, long categoryId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C(
		long groupId, long categoryId);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C(
		long groupId, long categoryId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_C_PrevAndNext(
			long messageId, long groupId, long categoryId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 */
	public void removeByG_C(long groupId, long categoryId);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message-boards messages
	 */
	public int countByG_C(long groupId, long categoryId);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_C(long groupId, long categoryId);

	/**
	 * Returns the message-boards message where groupId = &#63; and urlSubject = &#63; or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param urlSubject the url subject
	 * @return the matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_US(long groupId, String urlSubject)
		throws NoSuchMessageException;

	/**
	 * Returns the message-boards message where groupId = &#63; and urlSubject = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlSubject the url subject
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_US(long groupId, String urlSubject);

	/**
	 * Returns the message-boards message where groupId = &#63; and urlSubject = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlSubject the url subject
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_US(
		long groupId, String urlSubject, boolean useFinderCache);

	/**
	 * Removes the message-boards message where groupId = &#63; and urlSubject = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlSubject the url subject
	 * @return the message-boards message that was removed
	 */
	public MBMessage removeByG_US(long groupId, String urlSubject)
		throws NoSuchMessageException;

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and urlSubject = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlSubject the url subject
	 * @return the number of matching message-boards messages
	 */
	public int countByG_US(long groupId, String urlSubject);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_S(long groupId, int status);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_S_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_S_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_S_PrevAndNext(
			long messageId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_S(long groupId, int status);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_S_PrevAndNext(
			long messageId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public void removeByG_S(long groupId, int status);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByG_S(long groupId, int status);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_S(long groupId, int status);

	/**
	 * Returns all the message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_S(long companyId, int status);

	/**
	 * Returns a range of all the message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_S(
		long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByC_S_First(
			long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByC_S_Last(
			long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByC_S_PrevAndNext(
			long messageId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public void removeByC_S(long companyId, int status);

	/**
	 * Returns the number of message-boards messages where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByC_S(long companyId, int status);

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(long userId, long classNameId);

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(
		long userId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_First(
			long userId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_First(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_Last(
			long userId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_Last(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByU_C_PrevAndNext(
			long messageId, long userId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(
		long userId, long[] classNameIds);

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(
		long userId, long[] classNameIds, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(
		long userId, long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C(
		long userId, long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 */
	public void removeByU_C(long userId, long classNameId);

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching message-boards messages
	 */
	public int countByU_C(long userId, long classNameId);

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = any &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @return the number of matching message-boards messages
	 */
	public int countByU_C(long userId, long[] classNameIds);

	/**
	 * Returns all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C(long classNameId, long classPK);

	/**
	 * Returns a range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByC_C_PrevAndNext(
			long messageId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of message-boards messages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching message-boards messages
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_P(
		long threadId, long parentMessageId);

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_P_First(
			long threadId, long parentMessageId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_P_First(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_P_Last(
			long threadId, long parentMessageId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_P_Last(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByT_P_PrevAndNext(
			long messageId, long threadId, long parentMessageId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where threadId = &#63; and parentMessageId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 */
	public void removeByT_P(long threadId, long parentMessageId);

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and parentMessageId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param parentMessageId the parent message ID
	 * @return the number of matching message-boards messages
	 */
	public int countByT_P(long threadId, long parentMessageId);

	/**
	 * Returns all the message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_A(long threadId, boolean answer);

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_A(
		long threadId, boolean answer, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_A(
		long threadId, boolean answer, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_A(
		long threadId, boolean answer, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_A_First(
			long threadId, boolean answer,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_A_First(
		long threadId, boolean answer,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_A_Last(
			long threadId, boolean answer,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_A_Last(
		long threadId, boolean answer,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and answer = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByT_A_PrevAndNext(
			long messageId, long threadId, boolean answer,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where threadId = &#63; and answer = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 */
	public void removeByT_A(long threadId, boolean answer);

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and answer = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the number of matching message-boards messages
	 */
	public int countByT_A(long threadId, boolean answer);

	/**
	 * Returns all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_S(long threadId, int status);

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_S(
		long threadId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_S_First(
			long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_S_First(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_S_Last(
			long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_S_Last(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByT_S_PrevAndNext(
			long messageId, long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where threadId = &#63; and status = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 */
	public void removeByT_S(long threadId, int status);

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByT_S(long threadId, int status);

	/**
	 * Returns all the message-boards messages where threadId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_NotS(long threadId, int status);

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_NotS(
		long threadId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_NotS(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByT_NotS(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_NotS_First(
			long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_NotS_First(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByT_NotS_Last(
			long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByT_NotS_Last(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status &ne; &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByT_NotS_PrevAndNext(
			long messageId, long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where threadId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 */
	public void removeByT_NotS(long threadId, int status);

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByT_NotS(long threadId, int status);

	/**
	 * Returns all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByTR_S(long threadId, int status);

	/**
	 * Returns a range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByTR_S(
		long threadId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByTR_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByTR_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByTR_S_First(
			long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByTR_S_First(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByTR_S_Last(
			long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByTR_S_Last(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where threadId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByTR_S_PrevAndNext(
			long messageId, long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where threadId = &#63; and status = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 */
	public void removeByTR_S(long threadId, int status);

	/**
	 * Returns the number of message-boards messages where threadId = &#63; and status = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByTR_S(long threadId, int status);

	/**
	 * Returns all the message-boards messages where parentMessageId = &#63; and status = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByP_S(
		long parentMessageId, int status);

	/**
	 * Returns a range of all the message-boards messages where parentMessageId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByP_S(
		long parentMessageId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where parentMessageId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByP_S(
		long parentMessageId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where parentMessageId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByP_S(
		long parentMessageId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where parentMessageId = &#63; and status = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByP_S_First(
			long parentMessageId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where parentMessageId = &#63; and status = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByP_S_First(
		long parentMessageId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where parentMessageId = &#63; and status = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByP_S_Last(
			long parentMessageId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where parentMessageId = &#63; and status = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByP_S_Last(
		long parentMessageId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where parentMessageId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByP_S_PrevAndNext(
			long messageId, long parentMessageId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where parentMessageId = &#63; and status = &#63; from the database.
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 */
	public void removeByP_S(long parentMessageId, int status);

	/**
	 * Returns the number of message-boards messages where parentMessageId = &#63; and status = &#63;.
	 *
	 * @param parentMessageId the parent message ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByP_S(long parentMessageId, int status);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U_S(
		long groupId, long userId, int status);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U_S(
		long groupId, long userId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_U_S_First(
			long groupId, long userId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_U_S_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_U_S_Last(
			long groupId, long userId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_U_S_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_U_S_PrevAndNext(
			long messageId, long groupId, long userId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_U_S(
		long groupId, long userId, int status);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_U_S(
		long groupId, long userId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_U_S_PrevAndNext(
			long messageId, long groupId, long userId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 */
	public void removeByG_U_S(long groupId, long userId, int status);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByG_U_S(long groupId, long userId, int status);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_U_S(long groupId, long userId, int status);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_T_First(
			long groupId, long categoryId, long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_T_First(
		long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_T_Last(
			long groupId, long categoryId, long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_T_Last(
		long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_C_T_PrevAndNext(
			long messageId, long groupId, long categoryId, long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T(
		long groupId, long categoryId, long threadId);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_C_T_PrevAndNext(
			long messageId, long groupId, long categoryId, long threadId,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 */
	public void removeByG_C_T(long groupId, long categoryId, long threadId);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages
	 */
	public int countByG_C_T(long groupId, long categoryId, long threadId);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_C_T(long groupId, long categoryId, long threadId);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_S(
		long groupId, long categoryId, int status);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_S_First(
			long groupId, long categoryId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_S_Last(
			long groupId, long categoryId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_C_S_PrevAndNext(
			long messageId, long groupId, long categoryId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_S(
		long groupId, long categoryId, int status);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_C_S_PrevAndNext(
			long messageId, long groupId, long categoryId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	public void removeByG_C_S(long groupId, long categoryId, int status);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByG_C_S(long groupId, long categoryId, int status);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_C_S(long groupId, long categoryId, int status);

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C(
		long userId, long classNameId, long classPK);

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C(
		long userId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C(
		long userId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C(
		long userId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_C_First(
			long userId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_C_First(
		long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_C_Last(
			long userId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_C_Last(
		long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByU_C_C_PrevAndNext(
			long messageId, long userId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByU_C_C(long userId, long classNameId, long classPK);

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching message-boards messages
	 */
	public int countByU_C_C(long userId, long classNameId, long classPK);

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long classNameId, int status);

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long classNameId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long classNameId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long classNameId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_S_First(
			long userId, long classNameId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_S_First(
		long userId, long classNameId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_S_Last(
			long userId, long classNameId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_S_Last(
		long userId, long classNameId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByU_C_S_PrevAndNext(
			long messageId, long userId, long classNameId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long[] classNameIds, int status);

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long[] classNameIds, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long[] classNameIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_S(
		long userId, long[] classNameIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 */
	public void removeByU_C_S(long userId, long classNameId, int status);

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByU_C_S(long userId, long classNameId, int status);

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = any &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameIds the class name IDs
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByU_C_S(long userId, long[] classNameIds, int status);

	/**
	 * Returns all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C_S(
		long classNameId, long classPK, int status);

	/**
	 * Returns a range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C_S(
		long classNameId, long classPK, int status, int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C_S(
		long classNameId, long classPK, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByC_C_S(
		long classNameId, long classPK, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByC_C_S_First(
			long classNameId, long classPK, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByC_C_S_First(
		long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByC_C_S_Last(
			long classNameId, long classPK, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByC_C_S_Last(
		long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByC_C_S_PrevAndNext(
			long messageId, long classNameId, long classPK, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 */
	public void removeByC_C_S(long classNameId, long classPK, int status);

	/**
	 * Returns the number of message-boards messages where classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByC_C_S(long classNameId, long classPK, int status);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer, int start,
		int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_T_A_First(
			long groupId, long categoryId, long threadId, boolean answer,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_T_A_First(
		long groupId, long categoryId, long threadId, boolean answer,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_T_A_Last(
			long groupId, long categoryId, long threadId, boolean answer,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_T_A_Last(
		long groupId, long categoryId, long threadId, boolean answer,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_C_T_A_PrevAndNext(
			long messageId, long groupId, long categoryId, long threadId,
			boolean answer,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer, int start,
		int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_C_T_A_PrevAndNext(
			long messageId, long groupId, long categoryId, long threadId,
			boolean answer,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 */
	public void removeByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the number of matching message-boards messages
	 */
	public int countByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and answer = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param answer the answer
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_C_T_A(
		long groupId, long categoryId, long threadId, boolean answer);

	/**
	 * Returns all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status);

	/**
	 * Returns a range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_T_S_First(
			long groupId, long categoryId, long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_T_S_First(
		long groupId, long categoryId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_C_T_S_Last(
			long groupId, long categoryId, long threadId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_C_T_S_Last(
		long groupId, long categoryId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByG_C_T_S_PrevAndNext(
			long messageId, long groupId, long categoryId, long threadId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T_S(
		long groupId, long categoryId, long threadId, int status);

	/**
	 * Returns a range of all the message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the message-boards messages that the user has permissions to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages that the user has permission to view
	 */
	public java.util.List<MBMessage> filterFindByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] filterFindByG_C_T_S_PrevAndNext(
			long messageId, long groupId, long categoryId, long threadId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 */
	public void removeByG_C_T_S(
		long groupId, long categoryId, long threadId, int status);

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByG_C_T_S(
		long groupId, long categoryId, long threadId, int status);

	/**
	 * Returns the number of message-boards messages that the user has permission to view where groupId = &#63; and categoryId = &#63; and threadId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param threadId the thread ID
	 * @param status the status
	 * @return the number of matching message-boards messages that the user has permission to view
	 */
	public int filterCountByG_C_T_S(
		long groupId, long categoryId, long threadId, int status);

	/**
	 * Returns all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @return the matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C_S(
		long userId, long classNameId, long classPK, int status);

	/**
	 * Returns a range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C_S(
		long userId, long classNameId, long classPK, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C_S(
		long userId, long classNameId, long classPK, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message-boards messages
	 */
	public java.util.List<MBMessage> findByU_C_C_S(
		long userId, long classNameId, long classPK, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_C_S_First(
			long userId, long classNameId, long classPK, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the first message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_C_S_First(
		long userId, long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByU_C_C_S_Last(
			long userId, long classNameId, long classPK, int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Returns the last message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByU_C_C_S_Last(
		long userId, long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns the message-boards messages before and after the current message-boards message in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param messageId the primary key of the current message-boards message
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage[] findByU_C_C_S_PrevAndNext(
			long messageId, long userId, long classNameId, long classPK,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
				orderByComparator)
		throws NoSuchMessageException;

	/**
	 * Removes all the message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 */
	public void removeByU_C_C_S(
		long userId, long classNameId, long classPK, int status);

	/**
	 * Returns the number of message-boards messages where userId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @return the number of matching message-boards messages
	 */
	public int countByU_C_C_S(
		long userId, long classNameId, long classPK, int status);

	/**
	 * Returns the message-boards message where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching message-boards message
	 * @throws NoSuchMessageException if a matching message-boards message could not be found
	 */
	public MBMessage findByG_ERC(long groupId, String externalReferenceCode)
		throws NoSuchMessageException;

	/**
	 * Returns the message-boards message where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Returns the message-boards message where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public MBMessage fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the message-boards message where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the message-boards message that was removed
	 */
	public MBMessage removeByG_ERC(long groupId, String externalReferenceCode)
		throws NoSuchMessageException;

	/**
	 * Returns the number of message-boards messages where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching message-boards messages
	 */
	public int countByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Caches the message-boards message in the entity cache if it is enabled.
	 *
	 * @param mbMessage the message-boards message
	 */
	public void cacheResult(MBMessage mbMessage);

	/**
	 * Caches the message-boards messages in the entity cache if it is enabled.
	 *
	 * @param mbMessages the message-boards messages
	 */
	public void cacheResult(java.util.List<MBMessage> mbMessages);

	/**
	 * Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	 *
	 * @param messageId the primary key for the new message-boards message
	 * @return the new message-boards message
	 */
	public MBMessage create(long messageId);

	/**
	 * Removes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message that was removed
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage remove(long messageId) throws NoSuchMessageException;

	public MBMessage updateImpl(MBMessage mbMessage);

	/**
	 * Returns the message-boards message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message
	 * @throws NoSuchMessageException if a message-boards message with the primary key could not be found
	 */
	public MBMessage findByPrimaryKey(long messageId)
		throws NoSuchMessageException;

	/**
	 * Returns the message-boards message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message, or <code>null</code> if a message-boards message with the primary key could not be found
	 */
	public MBMessage fetchByPrimaryKey(long messageId);

	/**
	 * Returns all the message-boards messages.
	 *
	 * @return the message-boards messages
	 */
	public java.util.List<MBMessage> findAll();

	/**
	 * Returns a range of all the message-boards messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of message-boards messages
	 */
	public java.util.List<MBMessage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the message-boards messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message-boards messages
	 */
	public java.util.List<MBMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the message-boards messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message-boards messages
	 */
	public java.util.List<MBMessage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBMessage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the message-boards messages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of message-boards messages.
	 *
	 * @return the number of message-boards messages
	 */
	public int countAll();

}