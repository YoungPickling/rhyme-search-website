package lt.rimuok.controller;

import lombok.RequiredArgsConstructor;
import lt.rimuok.dto.JoinedTable;
import lt.rimuok.repository.JunctionTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rest")
@RequiredArgsConstructor
public class DictionaryController {
    @Autowired
    private JunctionTableRepository junctionTableRepository;

    @GetMapping("/")
    public List<JoinedTable> index(@RequestParam(name = "search") String search) {

        List<JoinedTable> response = junctionTableRepository.customQuery(search);
        System.out.println("Привет");
        return response;
    }

}
