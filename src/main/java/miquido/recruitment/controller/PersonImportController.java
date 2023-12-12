package miquido.recruitment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import miquido.recruitment.service.PersonImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Import")
@RequiredArgsConstructor
@RestController
@RequestMapping("/import")
public class PersonImportController {

    private final PersonImportService personImportService;

    @Operation(operationId = "import", description = "Import Star Wars hero from SWAPI to database")
    @PostMapping("/{id}")
    ResponseEntity<?> importPerson(@PathVariable Long id) {
        personImportService.importPerson(id);

        return ResponseEntity.ok().build();
    }
}
