package luisfrl01.com.github.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void createDB() {
        KeySchemaElement keySchema = new KeySchemaElement();
        keySchema.setKeyType(KeyType.HASH);
        keySchema.setAttributeName("clientId");

        AttributeDefinition attributeDefinition = new AttributeDefinition();
        attributeDefinition.setAttributeName("clientId");
        attributeDefinition.setAttributeType(ScalarAttributeType.S);

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput();
        provisionedThroughput.setReadCapacityUnits(10L);
        provisionedThroughput.setWriteCapacityUnits(5L);

        CreateTableRequest createTableRequest = new CreateTableRequest();
        createTableRequest.setKeySchema(List.of(keySchema));
        createTableRequest.setAttributeDefinitions(List.of(attributeDefinition));
        createTableRequest.setProvisionedThroughput(provisionedThroughput);
        createTableRequest.setTableName("client");

        amazonDynamoDB.createTable(createTableRequest);
    }

    public Client save(Client client) {
        dynamoDBMapper.save(client);
        return client;
    }

    public Client getClientById(String clienteId) {
        return dynamoDBMapper.load(Client.class, clienteId);
    }

    public List<Client> getClients() {
        return dynamoDBMapper.scan(Client.class, new DynamoDBScanExpression());
    }

    public String delete(String clienteId) {
        Client funcionario = dynamoDBMapper.load(Client.class, clienteId);
        dynamoDBMapper.delete(funcionario);
        return "Client deleted!!";
    }

    public Client update(String clienteId, Client client) {
        dynamoDBMapper.save(client,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("clientId",
                                new ExpectedAttributeValue(new AttributeValue().withS(clienteId))));
        return client;
    }
}
