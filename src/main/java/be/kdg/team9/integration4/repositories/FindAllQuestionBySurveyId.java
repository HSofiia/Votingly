package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Question;

import java.util.List;

public interface FindAllQuestionBySurveyId {
    List<Question> findAllBySurveyIdFetched(Long id);
}