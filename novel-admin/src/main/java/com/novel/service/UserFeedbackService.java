package com.novel.service;


import com.novel.entity.UserFeedback;

import java.util.List;

public interface UserFeedbackService {
    UserFeedback get(Long id);

    List<UserFeedback> list();

    long count();

    int save(UserFeedback user);

    int update(UserFeedback user);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
