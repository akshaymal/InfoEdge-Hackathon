package com.nnacres.assessment.helper;

import com.nnacres.assessment.dto.ContestListResponseDTO;
import com.nnacres.assessment.entity.Contest;

import java.util.ArrayList;
import java.util.List;

public class ContestHelper {

	public static List<ContestListResponseDTO> createContestDetailsByUser(List<Contest> contests){
		List<ContestListResponseDTO> contestDetailsByUserList = new ArrayList<ContestListResponseDTO>();
		for(Contest contest: contests){
			ContestListResponseDTO contestDetailsByUser = new ContestListResponseDTO(contest);
			contestDetailsByUserList.add(contestDetailsByUser);

		}
		return contestDetailsByUserList;
	}

}