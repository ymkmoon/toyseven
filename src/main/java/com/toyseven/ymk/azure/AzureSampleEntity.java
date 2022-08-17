package com.toyseven.ymk.azure;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AzureSampleEntity extends TableServiceEntity{
	
	String tempId;
	String tempName;
	
	public AzureSampleEntity(String lastName, String firstName) {
		this.partitionKey = lastName;
		this.rowKey = firstName;
	}
	
}
