package lk.icbt.megacity.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponseDTO<T> {
    private long total;
    private int page_size;
    private int page;
    private int total_pages;
    private boolean has_next_page;
    private boolean has_previous_page;
    private List<T> data;

    public PagedResponseDTO(Page<T> page) {
        this.total = page.getTotalElements();
        this.page_size = page.getSize();
        this.page = page.getNumber()+1;
        this.total_pages = page.getTotalPages();
        this.has_next_page = page.hasNext();
        this.has_previous_page = page.hasPrevious();
        this.data = page.getContent();
    }
}
