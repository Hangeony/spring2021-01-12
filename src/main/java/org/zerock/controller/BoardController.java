package org.zerock.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;
import org.zerock.service.FileUpService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@AllArgsConstructor //자동 인셉션이됨
@RequestMapping("/board/*")
public class BoardController {
	
	private BoardService service;
	private FileUpService fileUpSvc;
	
	/*
	 * @AllArgsConstructor
	   public BoardController(BoardService service) {
	 *  	this.service = service; }
	

	//211p 표 참고
//	@RequestMapping(value = "/list", method= RequestMethod.GET)
	@GetMapping("/list")
	//handler메소드의 return type이 void인 경우 요청경로가 view(jsp)의 이름이 됨 이메소드는 (/board/list) - > /board/list.jsp
	public void list(Model model) {
//		log.info("-----------------list--------------");
		List<BoardVO> list = service.getList();
		model.addAttribute("list", list);
	}
*/	
	@GetMapping("/list")
	public void list(@ModelAttribute("cri") Criteria cri  ,Model model) {
		List<BoardVO> list = service.getList(cri);
		
		int total = service.getTotal(cri);
		PageDTO dto = new PageDTO(cri, total);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", dto);
	}
	
//	@RequestMapping(value="register" ,method = RequestMethod.POST)
	@PostMapping("/register")
	public String register(BoardVO board,MultipartFile file, RedirectAttributes rttr) {
		
		/*
		BoardVO board = new Board();
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setWriter(request.getParameter("writer"));
		*/
		board.setFilename("");
		service.register(board);
		
		if (file != null) {
			board.setFilename(board.getBno() + "_" + file.getOriginalFilename());
			service.modify(board);
//			fileUpSvc.write(file, board.getFilename());
			try {
				fileUpSvc.transfer(file, board.getFilename());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		rttr.addFlashAttribute("result", board.getBno());
		rttr.addFlashAttribute("message", board.getBno() + "번이 등록되었습니다.");
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/register")
	public void register(@ModelAttribute("cri") Criteria cri) {
		
	}
	
	//책에는 경로 /board/get
//	@RequestMapping(value = "/get", method = RequestMethod.GET )
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri ,Model model) {
		/** 예전 코드 (스프링 없이) 
		String boardNum = request.getParameter("num");
		int num = Integer.parseInt(boardNum);
		
		BoardVO vo = service.get((long) num);
		
		request.setAttribute("board", vo);
		
		request.getRequestDispatcher(".jsp").forward();
		*/
		
		log.info("get method - bno : " + bno);
		log.info(cri);
		BoardVO vo = service.get(bno);
		model.addAttribute("board", vo);
//		model.addAttribute("cri", cri);
	}
	/*
	@GetMapping("/modify")
	public void modify(Long bno, Model model) {
		BoardVO vo = service.get(bno);
		model.addAttribute("board", vo);
	} 코드가 중복되므로 두번쓸 필요가없다.
	 */

	//	@RequestMapping(value = "/modify" ,method = RequestMethod.POST)
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		/**스프링 없이/ 위에 적어둠으로써 스프링이 알아서 처리해줌.
		 * BoardVO board = new BoardVO();  
		 * 원래는 long타입으로 변환하고 해야됨 
		 * board.setBno(request.getParameter("bno"));
		 * board.setTitle(request.getParameter("title"));
		 * board.setContent(request.getParameter("content"));
		 * 
		 * */
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success"); // 성공했을 때만 넣어줌
			rttr.addFlashAttribute("message", board.getBno() + "번 글이 수정되었습니다.");
		}
		
		service.modify(board);
		log.info(cri);
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type" , cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
	}
//	@RequestMapping(value = "/remove" , method = RequestMethod.POST)
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,Criteria cri, RedirectAttributes rttr) { // 같은 이름이면 @RequestParam("bno")를 생략가능함
		
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
			rttr.addFlashAttribute("message", bno + "번 글이 삭제되었습니다.");
		}
		
		service.remove(bno);
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type" , cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
	}

}
//servlet/jsp
//ControllerUsingURI (Servlet) ...properties
// /list.do - > ListHandler 이내요잉 properties에 있었음 
//그러면 Servlet이 properties를 읽어서 일처리함