package life.melon.community.service;

import life.melon.community.Mapper.QuestionMapper;
import life.melon.community.Mapper.UserMapper;
import life.melon.community.dto.PageDTO;
import life.melon.community.dto.QuestionDTO;
import life.melon.community.model.Question;
import life.melon.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//需要对多个mapper组合运用时
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageDTO list(String accountId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.count();
        pageDTO.setPagination(totalCount,page,size);

        if (page<1){
            page=1;
        }
        if (page>pageDTO.getTotalPage()){
            page=pageDTO.getTotalPage();
        }
        Integer offset = (page-1)*size;

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.countByUserID(userId);
        pageDTO.setPagination(totalCount,page,size);

        if (page<1){
            page=1;
        }
        if (page>pageDTO.getTotalPage()){
            page=pageDTO.getTotalPage();
        }
        Integer offset = (page-1)*size;

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;

    }
}
