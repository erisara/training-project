package com.iri.training.web.service;

import com.iri.training.enums.SubjectType;
import com.iri.training.model.interfaces.IPostable;

public interface VerificationService {

	boolean verifyPostable(IPostable postable);

	boolean verifyAddRights(IPostable postable, String authHeader);

	boolean verifyDeleteRights(SubjectType postableType, long postableId, String authHeader);

	boolean verifyEditRights(IPostable postable, String authHeader);
}
