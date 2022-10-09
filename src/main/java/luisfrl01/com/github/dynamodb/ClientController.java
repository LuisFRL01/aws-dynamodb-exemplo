package luisfrl01.com.github.dynamodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    ClienteRepository clienteRepository;

    @PostMapping
    public Client saveCliente(@RequestBody Client client) {
        return clienteRepository.save(client);
    }

    @GetMapping("/{clientId}")
    public Client getClientById(@PathVariable("clientId") String clienteId) {
        return clienteRepository.getClienteById(clienteId);
    }

    @PutMapping("/{clientId}")
    public Client updateClient(@PathVariable("clientId") String clienteId,
                               @RequestBody Client client) {
        return clienteRepository.update(clienteId, client);
    }

    @DeleteMapping("/{clientId}")
    public String delete(@PathVariable("clientId") String clienteId) {
        return  clienteRepository.delete(clienteId);
    }
}
