package com.toyseven.ymk.azure;

import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableBatchOperation;
import com.microsoft.azure.storage.table.TableOperation;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class AzureStorageComponent {
	
	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
			+ "AccountName=YOUT_STORAGE_ACCOUNT_NAME;"
			+ "AccountKey=YOUR_ACCOUNT_KEY;"
			+ "EndpointSuffix=core.windows.net";
	
	public void createTable(String tableName) {
		try {
			// 연결 문자열에서 스토리지 계정을 검색
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

			// 테이블 클라이언트를 생성
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// 테이블이 없는 경우 생성
			CloudTable cloudTable = tableClient.getTableReference(tableName);
			cloudTable.createIfNotExists();
		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
	}
	
	public void selectTableList() {
		try {
			// 연결 문자열에서 스토리지 계정을 검색합니다.
			CloudStorageAccount storageAccount =
				CloudStorageAccount.parse(storageConnectionString);

			// 테이블 클라이언트를 생성
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// 테이블 만큼 반복
			for (String table : tableClient.listTables()) {
				System.out.println(table);
			}
		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
	}
	
	public void insertData(String tableName, String partitionKey, String rowKey) {
		try {
			// 연결 문자열에서 스토리지 계정을 검색합니다.
		    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		    // 테이블 클라이언트를 생성
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();
		    // 배치 작업을 정의
		    TableBatchOperation batchOperation = new TableBatchOperation();
		    // 테이블에 대한 클라우드 테이블 개체를 생성
		    CloudTable cloudTable = tableClient.getTableReference(tableName);
		    
		    // 테이블에 추가할 엔티티를 생성
		    AzureSampleEntity sampleEntity = new AzureSampleEntity(partitionKey, rowKey);
		    sampleEntity.setTempId("azuretest-temp-id-1");
		    sampleEntity.setTempName("azuretest-temp-name-1");
		    batchOperation.insertOrReplace(sampleEntity);
		    
		    // 테이블에서 작업 배치를 실행
		    cloudTable.execute(batchOperation);
		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
	}
	
	public void selectOne(String tableName, String partitionKey, String rowKey) {
		try {
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
				CloudStorageAccount.parse(storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference(tableName);

			// Retrieve the entity with partition key of "Smith" and row key of "Jeff"
			TableOperation tableOperation =
				TableOperation.retrieve(partitionKey, rowKey, AzureSampleEntity.class);

			// Submit the operation to the table service and get the specific entity.
			AzureSampleEntity specificEntity =
				cloudTable.execute(tableOperation).getResultAsType();
			
			System.out.println("specificEntity : "+specificEntity);

			// Output the entity.
			if (specificEntity != null)
			{
				System.out.println(specificEntity.getPartitionKey() +
					" " + specificEntity.getRowKey() +
					"\t" + specificEntity.getTempId() +
					"\t" + specificEntity.getTempName());
			}
		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
	}
}
