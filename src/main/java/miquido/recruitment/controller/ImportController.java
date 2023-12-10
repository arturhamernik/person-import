package miquido.recruitment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import miquido.recruitment.service.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Import")
@RequiredArgsConstructor
@RestController
@RequestMapping("/import")
public class ImportController {

    private final ImportService importService;

    @Operation(operationId = "import", description = "Import Star Wars hero from SWAPI to database")
    @PostMapping("/{id}")
    ResponseEntity<?> importPerson(@PathVariable Long id) {
        importService.importPerson(id);

        return ResponseEntity.ok().build();
    }
}
