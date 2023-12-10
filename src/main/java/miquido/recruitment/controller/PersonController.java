package miquido.recruitment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Person")
@RequiredArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Operation(operationId = "get", description = "Get person from database by id")
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("id") String id) {
        return ResponseEntity.ok(personService.findPersonById(id));
    }

    @Operation(operationId = "search", description = "Search for person is database by name case inclusive")
    @GetMapping()
    public ResponseEntity<List<PersonDto>> findPerson(@RequestParam("name") String name) {
        return ResponseEntity.ok(personService.findPersonByName(name));
    }
}
