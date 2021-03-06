package com.lhj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lhj.model.Criteria;
import com.lhj.model.PageVO;
import com.lhj.model.ReviewVO;
import com.lhj.service.ReviewService;

@RestController
@RequestMapping("replies")
public class ReplyController {

	@Autowired
	private ReviewService rs;

	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	/* 댓글 등록 */
	@RequestMapping(value = "", method = RequestMethod.POST) // post는 등록
	public ResponseEntity<String> register(@RequestBody ReviewVO rv) {

		logger.info("ReviewVO :" + rv);
		ResponseEntity<String> entity = null;

		try {
			rs.repWrite(rv);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	/* 댓글 목록 */
	@RequestMapping(value = "/all/{pno}", method = RequestMethod.GET)
	public ResponseEntity<List> list(@PathVariable("pno") int pno) {

		logger.info("ReviewVO :" + pno);
		ResponseEntity<List> entity = null;

		try {
			entity = new ResponseEntity<List>(rs.repList(pno), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	/* 댓글 수정 */
	@RequestMapping(value="/{rno}", method= {RequestMethod.PUT ,RequestMethod.PATCH })
	public ResponseEntity<String> update(@PathVariable("rno") int rno , @RequestBody ReviewVO rv){
		
		logger.info("ReviewVO :"+rno);
		ResponseEntity<String> entity = null;
		
		try {
			rv.setRno(rno);
			rs.repModify(rv);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/* 댓글 삭제 */
	@RequestMapping(value="/{rno}", method= RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		
		logger.info("ReviewVO :"+rno);
		ResponseEntity<String> entity = null;
		
		try {
			rs.repDel(rno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	
	/* 댓글 페이징 목록 */
	@RequestMapping(value = "/{bno}/{pageNum}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") int bno
							,@PathVariable("pageNum") int pageNum) {

		logger.info("ReplyPage :" + bno+pageNum);
		ResponseEntity<Map<String, Object>> entity = null;

		try {
			Criteria cri = new Criteria();
			cri.setPageNum(pageNum);

			int cnt = rs.repCount(bno);
			
			PageVO pv = new PageVO(cri, cnt);
			List<ReviewVO> pagelist = rs.repListPage(bno, cri);
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", pagelist);
			map.put("pv", pv);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			logger.info("entity = "+entity);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	

}
