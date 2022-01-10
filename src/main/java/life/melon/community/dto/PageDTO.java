package life.melon.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPre;
    private boolean showFirst;
    private boolean showNext;
    private boolean showEnd;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;


    public void setPagination(Integer totalCount, Integer page, Integer size) {


        totalPage = (int)Math.ceil(totalCount *1.0/size);

        this.page = page;
        if (page==1){
            showPre = false;
        }else {
            showPre = true;
        }
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        if (pages.contains(1)){
            showFirst = false;
        }else {
            showFirst = true;
        }
        if (pages.contains(totalPage)){
            showEnd = false;
        }else {
            showEnd = true;
        }
        for (int i= page-3;i<=page+3;i++){
            if (i>=1&&i<=totalPage) pages.add(i);
        }

    }
}
