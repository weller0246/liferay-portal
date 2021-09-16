# Checks for .java

Check | Category | Description
----- | -------- | -----------
[AnnotationUseStyleCheck](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationUseStyle) | [Styling](styling_checks.markdown#styling-checks) | Checks the style of elements in annotations. |
[AnonymousClassCheck](checks/anonymous_class_check.markdown#anonymousclasscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for serialization issue when using anonymous class. |
AppendCheck | [Styling](styling_checks.markdown#styling-checks) | Checks instances where literal Strings are appended. |
ArquillianCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for correct use of `com.liferay.arquillian.extension.junit.bridge.junit.Arquillian`. |
[ArrayCheck](checks/array_check.markdown#arraycheck) | [Performance](performance_checks.markdown#performance-checks) | Checks if performance can be improved by using different mehods that can be used by collections. |
[ArrayTypeStyleCheck](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle) | [Styling](styling_checks.markdown#styling-checks) | Checks the style of array type definitions. |
AssertEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | Checks that additional information is provided when calling `Assert.assertEquals`. |
AssignAsUsedCheck | [Performance](performance_checks.markdown#performance-checks) | Finds cases where an assign statement can be inlined or moved closer to where it is used. |
[AttributeOrderCheck](checks/attribute_order_check.markdown#attributeordercheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that attributes in anonymous classes are ordered alphabetically. |
[AvoidNestedBlocksCheck](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks) | [Styling](styling_checks.markdown#styling-checks) | Finds nested blocks (blocks that are used freely in the code). |
[AvoidStarImportCheck](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there are no import statements that use the * notation. |
[CamelCaseNameCheck](checks/camel_case_name_check.markdown#camelcasenamecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks variable names for correct use of `CamelCase`. |
[ChainingCheck](checks/chaining_check.markdown#chainingcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that chaining is only applied on certain types and methods. |
[CompanyIterationCheck](checks/company_iteration_check.markdown#companyiterationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that `CompanyLocalService.forEachCompany` or `CompanyLocalService.forEachCompanyId` is used when iterating over companies |
CompatClassImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that classes are imported from `compat` modules, when possible. |
ConcatCheck | [Performance](performance_checks.markdown#performance-checks) | Checks for correct use of `StringBundler.concat`. |
ConstantNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that variable names of constants follow correct naming rules. |
ConstructorGlobalVariableDeclarationCheck | [Performance](performance_checks.markdown#performance-checks) | Checks that initial values of global variables are not set in the constructor. |
[ConstructorMissingEmptyLineCheck](checks/constructor_missing_empty_line_check.markdown#constructormissingemptylinecheck) | [Styling](styling_checks.markdown#styling-checks) | Checks for line breaks when assiging variables in constructor. |
ConsumerTypeAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on classes with @ConsumerType annotation. |
ContractionsCheck | [Styling](styling_checks.markdown#styling-checks) | Finds contractions in Strings (such as `can't` or `you're`). |
[CopyrightCheck](checks/copyright_check.markdown#copyrightcheck) | [Styling](styling_checks.markdown#styling-checks) | Validates `copyright` header. |
[CreationMenuBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks that `CreationMenuBuilder` is used when possible. |
[DefaultComesLastCheck](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast) | [Styling](styling_checks.markdown#styling-checks) | Checks that the `default` is after all the cases in a `switch` statement. |
DeprecatedUsageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds calls to deprecated classes or methods. |
EmptyCollectionCheck | [Styling](styling_checks.markdown#styling-checks) | Checks that there are no calls to `Collections.EMPTY_LIST`, `Collections.EMPTY_MAP` or `Collections.EMPTY_SET`. |
EmptyConstructorCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds unnecessary empty constructors. |
EnumConstantDividerCheck | [Styling](styling_checks.markdown#styling-checks) | Find unnecessary empty lines between enum constants. |
EnumConstantOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Checks the order of enum constants. |
EqualClauseIfStatementsCheck | [Styling](styling_checks.markdown#styling-checks) | Finds consecutive if-statements with identical clauses. |
[ExceptionCheck](checks/exception_check.markdown#exceptioncheck) | [Performance](performance_checks.markdown#performance-checks) | Finds private methods that throw unnecessary exception. |
[ExceptionMessageCheck](checks/message_check.markdown#messagecheck) | [Styling](styling_checks.markdown#styling-checks) | Validates messages that are passed to exceptions. |
ExceptionVariableNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Validates variable names that have type `*Exception`. |
FactoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `*Factory` should be used when creating new instances of an object. |
FilterStringWhitespaceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing and unnecessary whitespace in the value of the filter string in `ServiceTrackerFactory.open` or `WaiterUtil.waitForFilter`. |
[FrameworkBundleCheck](checks/framework_bundle_check.markdown#frameworkbundlecheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that `org.osgi.framework.Bundle.getHeaders()` is not used. |
FullyQualifiedNameCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds cases where a Fully Qualified Name is used instead of importing a class. |
[GenericTypeCheck](checks/generic_type_check.markdown#generictypecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that generics are always specified to provide compile-time checking and removing the risk of `ClassCastException` during runtime. |
[GetterUtilCheck](checks/getter_util_check.markdown#getterutilcheck) | [Styling](styling_checks.markdown#styling-checks) | Finds cases where the default value is passed to `GetterUtil.get*` or `ParamUtil.get*`. |
[IfStatementCheck](checks/if_statement_check.markdown#ifstatementcheck) | [Styling](styling_checks.markdown#styling-checks) | Finds empty if-statements and consecutive if-statements with identical bodies |
InstanceofOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Check the order of `instanceof` calls. |
[ItemListBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks that `DropdownItemListBuilder`, `LabelItemListBuilder` or `NavigationItemListBuilder` is used when possible. |
JSONNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks if variable names follow naming conventions. |
[JSONUtilCheck](checks/json_util_check.markdown#jsonutilcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks for utilization of class `JSONUtil`. |
Java2HTMLCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds incorrect use of `.java.html` in `.jsp` files. |
[JavaAPISignatureCheck](checks/java_api_signature_check.markdown#javaapisignaturecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that types `HttpServletRequest`, `HttpServletResponse`, `ThemeDisplay`, and `ServiceContext` are not used in API method signatures. |
JavaAbstractMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds incorrect `abstract` methods in `interface`. |
JavaAggregateTestRuleParameterOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Checks the order of parameters in `new AggregateTestRule` calls. |
JavaAnnotationDefaultAttributeCheck | [Styling](styling_checks.markdown#styling-checks) | Finds cases where the default value is passed to annotations in package `*.bnd.annotations` or `*.bind.annotations`. |
JavaAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on annotations. |
[JavaAnonymousInnerClassCheck](checks/java_anonymous_inner_class_check.markdown#javaanonymousinnerclasscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on anonymous classes. |
JavaAssertEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | Validates `Assert.assertEquals` calls. |
[JavaBaseUpgradeCallableCheck](checks/java_base_upgrade_callable_check.markdown#javabaseupgradecallablecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that BaseUpgradeCallable is used instead of Callable or Runnable in Upgrade and Verify classes. |
JavaBooleanStatementCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on variable declaration of type `Boolean`. |
JavaBooleanUsageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds incorrect use of passing boolean values in `setAttribute` calls. |
JavaClassNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks if class names follow naming conventions. |
JavaCleanUpMethodSuperCleanUpCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that `cleanUp` method in `*Tag` class with `@Override` annotation calls the `cleanUp` method of the superclass. |
[JavaCleanUpMethodVariablesCheck](checks/java_clean_up_method_variables_check.markdown#javacleanupmethodvariablescheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that variables in `Tag` classes get cleaned up properly. |
[JavaCollatorUtilCheck](checks/java_collator_util_check.markdown#javacollatorutilcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for correct use of `Collator`. |
[JavaComponentActivateCheck](checks/java_component_activate_check.markdown#javacomponentactivatecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks if methods with annotation `@Activate` or `@Deactivate` follow naming conventions. |
JavaComponentAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on classes with `@Component` annotation. |
[JavaConfigurationAdminCheck](checks/java_configuration_admin_check.markdown#javaconfigurationadmincheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for correct use of `location == ?` when calling `org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration`. |
[JavaConfigurationCategoryCheck](checks/java_configuration_category_check.markdown#javaconfigurationcategorycheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that the value of `category` in `@ExtendedObjectClassDefinition` matches the `categoryKey` of the corresponding class in `configuration-admin-web`. |
[JavaConstructorParametersCheck](checks/java_constructor_parameters_check.markdown#javaconstructorparameterscheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that the order of variable assignments matches the order of the parameters in the constructor signature. |
JavaConstructorSuperCallCheck | [Styling](styling_checks.markdown#styling-checks) | Finds unnecessary call to no-argument constructor of the superclass. |
JavaDeprecatedJavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | Checks if the `@deprecated` javadoc is pointing to the correct version. |
JavaDeprecatedKernelClassesCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds calls to deprecated classes `com.liferay.portal.kernel.util.CharPool` and `com.liferay.portal.kernel.util.StringPool`. |
JavaDeserializationSecurityCheck | [Security](security_checks.markdown#security-checks) | Finds Java serialization vulnerabilities. |
JavaDiamondOperatorCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds cases where Diamond Operator is not used. |
JavaDuplicateVariableCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds variables where a variable with the same name already exists in an extended class. |
[JavaElseStatementCheck](checks/java_else_statement_check.markdown#javaelsestatementcheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds unnecessary `else` statements (when the `if` statement ends with a `return` statement). |
JavaEmptyLineAfterSuperCallCheck | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds missing emptly line after a `super` call. |
JavaEmptyLinesCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing and unnecessary empty lines. |
JavaExceptionCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that variable names of exceptions in `catch` statements follow naming conventions. |
JavaFinalVariableCheck | [Styling](styling_checks.markdown#styling-checks) | Finds cases of unneeded `final` modifiers for variables and parameters. |
[JavaFinderCacheCheck](checks/java_finder_cache_check.markdown#javafindercachecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that the method `BasePersistenceImpl.fetchByPrimaryKey` is overridden, when using `FinderPath`. |
JavaFinderImplCustomSQLCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that hardcoded SQL values in `*FinderImpl` classes match the SQL in the `.xml` file in the `custom-sql` directory. |
[JavaForLoopCheck](checks/java_for_loop_check.markdown#javaforloopcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks if a Enhanced For Loop can be used instead of a Simple For Loop. |
[JavaHelperUtilCheck](checks/java_helper_util_check.markdown#javahelperutilcheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Finds incorrect use of `*Helper` or `*Util` classes. |
JavaHibernateSQLCheck | [Performance](performance_checks.markdown#performance-checks) | Finds calls to `com.liferay.portal.kernel.dao.orm.Session.createSQLQuery` (use `Session.createSynchronizedSQLQuery` instead). |
JavaIOExceptionCheck | [Styling](styling_checks.markdown#styling-checks) | Validates use of `IOException`. |
JavaIgnoreAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds methods with `@Ignore` annotation in test classes. |
JavaIllegalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases of incorrect use of certain classes. |
JavaImportsCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts and groups imports in `.java` files. |
[JavaIndexableCheck](checks/java_indexable_check.markdown#javaindexablecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that the type gets returned when using annotation `@Indexable`. |
JavaInnerClassImportsCheck | [Styling](styling_checks.markdown#styling-checks) | Finds cases where inner classes are imported. |
JavaInterfaceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that `interface` is not `static`. |
JavaInternalPackageCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on class in `internal` package. |
JavaJSPDynamicIncludeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `*JSPDynamicInclude` class. |
[JavaLocalSensitiveComparisonCheck](checks/java_local_sensitive_comparison_check.markdown#javalocalsensitivecomparisoncheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that `java.text.Collator` is used when comparing localized values. |
JavaLogClassNameCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks the name of the class that is passed in `LogFactoryUtil.getLog`. |
[JavaLogLevelCheck](checks/java_log_level_check.markdown#javaloglevelcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that the correct log messages are printed. |
JavaLongLinesCheck | [Styling](styling_checks.markdown#styling-checks) | Finds lines that are longer than the specified maximum line length. |
JavaMapBuilderGenericsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing or unnecessary generics on `*MapBuilder.put` calls. |
[JavaMetaAnnotationsCheck](checks/java_meta_annotations_check.markdown#javametaannotationscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for correct use of attributes `description` and `name` in annotation `@aQute.bnd.annotation.metatype.Meta`. |
JavaMissingXMLPublicIdsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing public IDs for check XML files. |
JavaModifiedServiceMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing empty lines before `removedService` or `addingService` calls. |
[JavaModuleComponentCheck](checks/java_module_component_check.markdown#javamodulecomponentcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for use of `@Component` in `-api` or `-spi` modules. |
[JavaModuleExposureCheck](checks/java_module_exposure_check.markdown#javamoduleexposurecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for exposure of `SPI` types in `API`. |
JavaModuleIllegalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases of incorrect use of certain classes in modules. |
JavaModuleInternalImportsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where a module imports an `internal` class from another class. |
JavaModuleJavaxPortletInitParamTemplatePathCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the value of `javax.portlet.init-param.template-path`. |
JavaModuleServiceProxyFactoryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases of `ServiceProxyFactory.newServiceTrackedInstance`. |
JavaModuleServiceReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `@BeanReference` annotation should be used instead of `@ServiceReference` annotation. |
[JavaModuleTestCheck](checks/java_module_test_check.markdown#javamoduletestcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks package names in tests. |
[JavaMultiPlusConcatCheck](checks/java_multi_plus_concat_check.markdown#javamultiplusconcatcheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that we do not concatenate more than 3 String objects. |
[JavaOSGiReferenceCheck](checks/java_osgi_reference_check.markdown#javaosgireferencecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on classes with `@Component` annotation. |
[JavaPackagePathCheck](checks/java_package_path_check.markdown#javapackagepathcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that the package name matches the file location. |
JavaParameterAnnotationsCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on parameters with annotations. |
[JavaProcessCallableCheck](checks/java_process_callable_check.markdown#javaprocesscallablecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that a class implementing `ProcessCallable` assigns a `serialVersionUID`. |
JavaProviderTypeAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on classes with `@ProviderType` annotation. |
JavaRedundantConstructorCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds unnecessary empty constructor. |
JavaReleaseInfoCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates information in `ReleaseInfo.java`. |
[JavaResultSetCheck](checks/java_result_set_check.markdown#javaresultsetcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for correct use `java.sql.ResultSet.getInt(int)`. |
JavaReturnStatementCheck | [Styling](styling_checks.markdown#styling-checks) | Finds unnecessary `else` statement (when `if` and `else` statement both end with `return` statement). |
[JavaSeeAnnotationCheck](checks/java_see_annotation_check.markdown#javaseeannotationcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for nested annotations inside `@see`. |
JavaServiceImplCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Ensures that `afterPropertiesSet` and `destroy` methods in `*ServiceImpl` always call the method with the same name in the superclass. |
JavaServiceObjectCheck | [Styling](styling_checks.markdown#styling-checks) | Checks for correct use of `*.is*` instead of `*.get*` when calling methods generated by ServiceBuilder. |
[JavaServiceTrackerFactoryCheck](checks/java_service_tracker_factory_check.markdown#javaservicetrackerfactorycheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that there are no calls to deprecatred method `ServiceTrackerFactory.open(java.lang.Class)`. |
[JavaServiceUtilCheck](checks/java_service_util_check.markdown#javaserviceutilcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there are no calls to `*ServiceImpl` from a `*ServiceUtil` class. |
JavaSessionCheck | [Performance](performance_checks.markdown#performance-checks) | Finds unnecessary calls to `Session.flush()` (calls that are followed by `Session.clear()`). |
[JavaSignatureParametersCheck](checks/java_signature_parameters_check.markdown#javasignatureparameterscheck) | [Styling](styling_checks.markdown#styling-checks) | Checks the order of parameters. |
JavaSourceFormatterDocumentationCheck | [Documentation](documentation_checks.markdown#documentation-checks) | Finds SourceFormatter checks that have no documentation. |
JavaStagedModelDataHandlerCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing method `setMvccVersion` in class extending `BaseStagedModelDataHandler` in module that has `mvcc-enabled=true` in `service.xml`. |
JavaStaticBlockCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on `static` blocks. |
[JavaStaticImportsCheck](checks/java_static_imports_check.markdown#javastaticimportscheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that there are no static imports. |
JavaStaticMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where methods are unncessarily declared static. |
JavaStaticVariableDependencyCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that static variables in the same class that depend on each other are correctly defined. |
[JavaStopWatchCheck](checks/java_stop_watch_check.markdown#javastopwatchcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for potential NullPointerException when using `StopWatch`. |
[JavaStringBundlerConcatCheck](checks/java_string_bundler_concat_check.markdown#javastringbundlerconcatcheck) | [Performance](performance_checks.markdown#performance-checks) | Finds calls to `StringBundler.concat` with less than 3 parameters. |
JavaStringBundlerInitialCapacityCheck | [Performance](performance_checks.markdown#performance-checks) | Checks the initial capacity of new instances of `StringBundler`. |
JavaStringStartsWithSubstringCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for uses of `contains` followed by `substring`, which should be `startsWith` instead. |
JavaStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consisteny in code style. |
[JavaSwitchCheck](checks/java_switch_check.markdown#javaswitchcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that `if/else` statement is used instead of `switch` statement. |
JavaSystemEventAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing method `setDeletionSystemEventStagedModelTypes` in class with annotation @SystemEvent. |
JavaSystemExceptionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds unnecessary SystemExceptions. |
JavaTaglibMethodCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that a `*Tag` class has a `set*` and `get*` or `is*` method for each attribute. |
JavaTermDividersCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing or unnecessary empty lines between javaterms. |
JavaTermOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Checks the order of javaterms. |
JavaTermStylingCheck | [Styling](styling_checks.markdown#styling-checks) | Applies rules to enforce consisteny in code style. |
[JavaTestMethodAnnotationsCheck](checks/java_test_method_annotations_check.markdown#javatestmethodannotationscheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks if methods with test annotations follow the naming conventions. |
JavaTransactionBoundaryCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds direct `add*` or `get*` calls in `*ServiceImpl` (those should use the `*service` global variable instead). |
[JavaUnsafeCastingCheck](checks/java_unsafe_casting_check.markdown#javaunsafecastingcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for potential ClassCastException. |
[JavaUnusedSourceFormatterChecksCheck](checks/java_unused_source_formatter_checks_check.markdown#javaunusedsourceformattercheckscheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Finds `*Check` classes that are not configured. |
[JavaUpgradeClassCheck](checks/java_upgrade_class_check.markdown#javaupgradeclasscheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on Upgrade classes. |
JavaUpgradeConnectionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
[JavaUpgradeIndexCheck](checks/java_upgrade_index_check.markdown#javaupgradeindexcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where the service builder indexes are updated manually in Upgrade classes. This is not needed because Liferay takes care of it. |
JavaUpgradeVersionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies that the correct upgrade versions are used in classes that implement `UpgradeStepRegistrator`. |
JavaVariableTypeCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on the modifiers on variables. |
JavaVerifyUpgradeConnectionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `DataAccess.getConnection` is used (instead of using the availabe global variable `connection`). |
JavaXMLSecurityCheck | [Security](security_checks.markdown#security-checks) | Finds possible XXE or Quadratic Blowup security vulnerabilities. |
JavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | Performs several checks on javadoc. |
[JavadocStyleCheck](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocStyle) | [Javadoc](javadoc_checks.markdown#javadoc-checks) | Validates Javadoc comments to help ensure they are well formed. |
LPS42924Check | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `PortalUtil.getClassName*` (instead of calling `classNameLocalService` directly). |
[LambdaCheck](checks/lambda_check.markdown#lambdacheck) | [Styling](styling_checks.markdown#styling-checks) | Checks that `lambda` statements are as simple as possible. |
LanguageKeysCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing language keys in `Language.properties`. |
[ListUtilCheck](checks/list_util_check.markdown#listutilcheck) | [Styling](styling_checks.markdown#styling-checks) | Checks for utilization of class `ListUtil`. |
LiteralStringEqualsCheck | [Styling](styling_checks.markdown#styling-checks) | Finds cases where `Objects.equals` should be used. |
[LocalFinalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalFinalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that local final variable names conform to a specified pattern. |
LocalPatternCheck | [Performance](performance_checks.markdown#performance-checks) | Checks that a `java.util.Pattern` variable is declared globally, so that it is initiated only once. |
[LocalVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#LocalVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that local, non-final variable names conform to a specified pattern. |
LocaleUtilCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `com.liferay.portal.kernel.util.LocaleUtil` should be used (instead of `java.util.Locale`). |
[LogMessageCheck](checks/message_check.markdown#messagecheck) | [Styling](styling_checks.markdown#styling-checks) | Validates messages that are passed to `log.*` calls. |
LogParametersCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Validates the values of parameters passed to `_log.*` calls. |
[MVCCommandNameCheck](checks/mvc_command_name_check.markdown#mvccommandnamecheck) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks for consistent naming for values of `mvc.command.name`. |
[MapBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks that `ConcurrentHashMapBuilder`, `HashMapBuilder`, `LinkedHashMapBuilder` or `TreeMapBuilder` is used when possible. |
[MapIterationCheck](checks/map_iteration_check.markdown#mapiterationcheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that there are no unnecessary map iterations. |
[MemberNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MemberName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that instance variable names conform to a specified pattern. |
MethodCallsOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Sorts method calls for certain object (for example, `put` calls in `java.util.HashMap`). |
[MethodNameCheck](https://checkstyle.sourceforge.io/config_naming.html#MethodName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that method names conform to a specified pattern. |
MethodNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that method names follow naming conventions. |
[MethodParamPadCheck](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad) | [Styling](styling_checks.markdown#styling-checks) | Checks the padding between the identifier of a method definition, constructor definition, method call, or constructor invocation; and the left parenthesis of the parameter list. |
MissingAuthorCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | Finds classes that have no `@author` specified. |
[MissingDeprecatedCheck](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDeprecatedJavadocCheck | [Javadoc](javadoc_checks.markdown#javadoc-checks) | Verifies that the annotation @Deprecated and the Javadoc tag @deprecated are both present when either of them is present. |
MissingDiamondOperatorCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks for missing diamond operator for types that require diamond operator. |
[MissingEmptyLineCheck](checks/missing_empty_line_check.markdown#missingemptylinecheck) | [Styling](styling_checks.markdown#styling-checks) | Checks for missing line breaks around variable declarations. |
MissingModifierCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies that a method or global variable has a modifier specified. |
MissingOverrideCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies that a method that overrides a method in a superclass has the @Override annotation. |
MissingParenthesesCheck | [Styling](styling_checks.markdown#styling-checks) | Finds missing parentheses in conditional statement. |
[ModifierOrderCheck](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder) | [Styling](styling_checks.markdown#styling-checks) | Checks that the order of modifiers conforms to the suggestions in the Java Language specification, § 8.1.1, 8.3.1, 8.4.3 and 9.4. |
[MultipleVariableDeclarationsCheck](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations) | [Styling](styling_checks.markdown#styling-checks) | Checks that each variable declaration is in its own statement and on its own line. |
NestedFieldAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies that `NestedFieldSupport.class` is used in `service` property of `Component` annotation |
NestedIfStatementCheck | [Styling](styling_checks.markdown#styling-checks) | Finds nested if statements that can be combined. |
[NoLineWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoLineWrap) | [Styling](styling_checks.markdown#styling-checks) | Checks that chosen statements are not line-wrapped. |
[NoWhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | Checks that there is no whitespace after a token. |
[NoWhitespaceBeforeCheck](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore) | [Styling](styling_checks.markdown#styling-checks) | Checks that there is no whitespace before a token. |
NotRequireThisCheck | [Styling](styling_checks.markdown#styling-checks) | Finds cases of unnecessary use of `this.`. |
NullAssertionInIfStatementCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Verifies that null check should always be first in if-statement. |
NumberSuffixCheck | [Styling](styling_checks.markdown#styling-checks) | Verifies that uppercase `D`, `F`, or `L` is used when denoting Double/Float/Long. |
[OSGiResourceBuilderCheck](checks/osgi_resource_builder_check.markdown#osgiresourcebuildercheck) | [Styling](styling_checks.markdown#styling-checks) | Avoid using *Resource.builder. |
[OneStatementPerLineCheck](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine) | [Styling](styling_checks.markdown#styling-checks) | Checks that there is only one statement per line. |
OperatorOperandCheck | [Styling](styling_checks.markdown#styling-checks) | Verifies that operand do not go over too many lines and make the operator hard to read. |
OperatorOrderCheck | [Styling](styling_checks.markdown#styling-checks) | Verifies that when an operator has a literal string or a number as one of the operands, it is always on the right hand side. |
[OperatorWrapCheck](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap) | [Styling](styling_checks.markdown#styling-checks) | Checks the policy on how to wrap lines on operators. |
[PackageNameCheck](https://checkstyle.sourceforge.io/config_naming.html#PackageName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that package names conform to a specified pattern. |
[ParameterNameCheck](https://checkstyle.sourceforge.io/config_naming.html#ParameterName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that method parameter names conform to a specified pattern. |
ParsePrimitiveTypeCheck | [Performance](performance_checks.markdown#performance-checks) | Verifies that `GetterUtil.parse*` is used to parse primitive types, when possible. |
PersistenceCallCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds illegal persistence calls across component boundaries. |
[PersistenceUpdateCheck](checks/persistence_update_check.markdown#persistenceupdatecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there are no stale references in service code from persistence updates. |
PlusStatementCheck | [Styling](styling_checks.markdown#styling-checks) | Performs several checks to statements where `+` is used for concatenation. |
[PortletURLBuilderCheck](checks/builder_check.markdown#buildercheck) | [Miscellaneous](miscellaneous_checks.markdown#miscellaneous-checks) | Checks that `PortletURLBuilder` is used when possible. |
PrimitiveWrapperInstantiationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where `new Type` is used for primitive types (use `Type.valueOf` instead). |
PrincipalExceptionCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds calls to `PrincipalException.class.getName()` (use `PrincipalException.getNestedClasses()` instead). |
RedundantBranchingStatementCheck | [Performance](performance_checks.markdown#performance-checks) | Finds unnecessary branching (`break`, `continue` or `return`) statements. |
ReferenceAnnotationCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on classes with @Reference annotation. |
[RequireThisCheck](https://checkstyle.sourceforge.io/config_coding.html#RequireThis) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that references to instance variables and methods of the present object are explicitly of the form 'this.varName' or 'this.methodName(args)' and that those references don't rely on the default behavior when 'this.' is absent. |
[ResourceBundleCheck](checks/resource_bundle_check.markdown#resourcebundlecheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Checks that there are no calls to `java.util.ResourceBundle.getBundle`. |
SelfReferenceCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases of unnecessary reference to its own class. |
SemiColonCheck | [Styling](styling_checks.markdown#styling-checks) | Finds cases of unnecessary semicolon. |
SessionKeysCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that messages send to `SessionsErrors` or `SessionMessages` follow naming conventions. |
SingleStatementClauseCheck | [Styling](styling_checks.markdown#styling-checks) | Verifies that `for`, `if` or `while` statement always uses curly braces. |
SizeIsZeroCheck | [Styling](styling_checks.markdown#styling-checks) | Finds cases of calls like `list.size() == 0` (use `list.isEmpty()` instead). |
[StaticBlockCheck](checks/static_block_check.markdown#staticblockcheck) | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Performs several checks on static blocks. |
[StaticVariableNameCheck](https://checkstyle.sourceforge.io/config_naming.html#StaticVariableName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that static, non-final variable names conform to a specified pattern. |
StringBundlerNamingCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks for consistent naming on variables of type 'StringBundler'. |
StringCastCheck | [Performance](performance_checks.markdown#performance-checks) | Finds cases where a redundant `toString()` is called on variable type `String`. |
[StringLiteralEqualityCheck](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality) | [Styling](styling_checks.markdown#styling-checks) | Checks that string literals are not used with == or !=. |
[StringMethodsCheck](checks/string_methods_check.markdown#stringmethodscheck) | [Performance](performance_checks.markdown#performance-checks) | Checks if performance can be improved by using different String operation methods. |
SubstringCheck | [Performance](performance_checks.markdown#performance-checks) | Finds cases like `s.substring(1, s.length())` (use `s.substring(1)` instead). |
TernaryOperatorCheck | [Styling](styling_checks.markdown#styling-checks) | Finds use of ternary operator in `java` files (use if statement instead). |
TestClassCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that names of test classes follow naming conventions. |
TestClassMissingLiferayUnitTestRuleCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds missing LiferayUnitTestRule. |
ThreadLocalUtilCheck | [Performance](performance_checks.markdown#performance-checks) | Finds new instances of `java.lang.Thread` (use `ThreadLocalUtil.create` instead). |
ThreadNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that names of threads follow naming conventions. |
TransactionalTestRuleCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds usage of `TransactionalTestRule` in `*StagedModelDataHandlerTest`. |
TryWithResourcesCheck | [Performance](performance_checks.markdown#performance-checks) | Ensures using Try-With-Resources statement to properly close the resource. |
[TypeNameCheck](https://checkstyle.sourceforge.io/config_naming.html#TypeName) | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that type names conform to a specified pattern. |
[UnnecessaryAssignCheck](checks/unnecessary_assign_check.markdown#unnecessaryassigncheck) | [Performance](performance_checks.markdown#performance-checks) | Finds unnecessary assign statements (when it is either reassigned or returned right after). |
UnnecessaryMethodCallCheck | [Styling](styling_checks.markdown#styling-checks) | Finds unnecessary method calls. |
[UnnecessaryParenthesesCheck](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses) | [Styling](styling_checks.markdown#styling-checks) | Checks if unnecessary parentheses are used in a statement or expression. |
UnnecessaryTypeCastCheck | [Performance](performance_checks.markdown#performance-checks) | Finds unnecessary Type Casting. |
[UnnecessaryVariableDeclarationCheck](checks/unnecessary_variable_declaration_check.markdown#unnecessaryvariabledeclarationcheck) | [Performance](performance_checks.markdown#performance-checks) | Finds unnecessary variable declarations (when it is either reassigned or returned right after). |
UnparameterizedClassCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds `Class` instantation without generic type. |
UnprocessedExceptionCheck | [Performance](performance_checks.markdown#performance-checks) | Finds cases where an `Exception` is swallowed without being processed. |
UnusedMethodCheck | [Performance](performance_checks.markdown#performance-checks) | Finds private methods that are not used. |
UnusedParameterCheck | [Performance](performance_checks.markdown#performance-checks) | Finds parameters in private methods that are not used. |
UnusedVariableCheck | [Performance](performance_checks.markdown#performance-checks) | Finds variables that are declared, but not used. |
UnwrappedVariableInfoCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Finds cases where the variable should be wrapped into an inner class in order to defer array elements initialization. |
[ValidatorEqualsCheck](checks/validator_equals_check.markdown#validatorequalscheck) | [Performance](performance_checks.markdown#performance-checks) | Checks that there are no calls to `Validator.equals(Object, Object)`. |
ValidatorIsNullCheck | [Bug Prevention](bug_prevention_checks.markdown#bug-prevention-checks) | Ensures that only variable of type `Long`, `Serializable` or `String` is passed to method `com.liferay.portal.kernel.util.Validator.isNull`. |
VariableDeclarationAsUsedCheck | [Performance](performance_checks.markdown#performance-checks) | Finds cases where a variable declaration can be inlined or moved closer to where it is used. |
VariableNameCheck | [Naming Conventions](naming_conventions_checks.markdown#naming-conventions-checks) | Checks that variable names follow naming conventions. |
[WhitespaceAfterCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter) | [Styling](styling_checks.markdown#styling-checks) | Checks that a token is followed by whitespace, with the exception that it does not check for whitespace after the semicolon of an empty for iterator. |
[WhitespaceAroundCheck](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround) | [Styling](styling_checks.markdown#styling-checks) | Checks that a token is surrounded by whitespace. |