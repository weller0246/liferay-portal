create index IX_8696D945 on ListTypeDefinition (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_C413932E on ListTypeEntry (listTypeDefinitionId, key_[$COLUMN_LENGTH:75$]);
create index IX_C8F46119 on ListTypeEntry (listTypeDefinitionId, name[$COLUMN_LENGTH:75$]);
create index IX_8F486D74 on ListTypeEntry (uuid_[$COLUMN_LENGTH:75$], companyId);