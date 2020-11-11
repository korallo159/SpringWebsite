package com.koral.webKoral;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiExample {

    private List<ApiQuotationExample> quotations;

    public ApiExample(){
        this.quotations = new ArrayList<>();
        quotations.add(new ApiQuotationExample("Uczciwosc jest cnotą która dużo kosztuje.", "Karol Irzydowski"));
        quotations.add(new ApiQuotationExample("Talent rośnie w samotności, charakter wśród ludzi.", "Johann Wolfgang von Goethe"));
    }

    @GetMapping("/api")
    public List<ApiQuotationExample> GetQuotation(){
        return quotations;
    }

    @PostMapping("/api")
    public boolean addQuotation(@RequestBody ApiQuotationExample quotation) {
        return quotations.add(quotation);
    }

    @DeleteMapping("/api")
    public void deleteQuotation(@RequestParam int index) {
        quotations.remove(index);
    }


}
