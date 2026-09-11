package main.java.com.rental.rental.service;

import java.sql.SQLException;
import java.util.List;


import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.rental.dto.ActionRequest;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.dto.RentalResponse;
import main.java.com.rental.rental.repository.RentalRepository;
import main.java.com.rental.rental.repository.RentalRepositoryImpl;

public class RentalServiceImpl implements RentalService {

	private RentalRepository rentalRepository = new RentalRepositoryImpl();
	
	 /*
     * 물품 조회  하기 
     */
    @Override
    public List<RentalResponse> findBorrowedItems(ActionRequest request) throws SQLException{

        
            List<RentalResponse> list =rentalRepository.findBorrowedItems(request);
            
            if (list.isEmpty()) {
    	        throw new NotFoundException("검색된 결과가 없습니다.");
    	    }
            return list;
  
    }
    
    /*
	 * 대여/반납 요청 
	 */
	@Override
    public int requestRental(RentalCreateRequest request) throws SQLException{
		int result = rentalRepository.requestRental(request);
		
		if(result == 0) {
			throw new RentalException("요청 도중 문제가 발생했습니다");
		}

	    return result;
	}

}
