package com.boardProject.board.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {
    //숫자리스트를 받아서 뷰에서 바로 그려주면 됨
    // 파라미터 : 현재 페이지 수, 계산을 하려면 전체 페이지 수를 알아야 하니까 마지막 페이지 번호

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages){
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH/2), 0); // 중앙에 오게끔 리스트를 만듦, math함수 사용하여 0보다 큰 수 사용
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber,endNumber).boxed().toList();


    }

    public int currentBarLength(){
        return BAR_LENGTH;
    }
}
