package luisfrl01.com.github.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Client save(Client client) {
        dynamoDBMapper.save(client);
        return client;
    }

    public Client getClienteById(String clienteId) {
        return dynamoDBMapper.load(Client.class, clienteId);
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
