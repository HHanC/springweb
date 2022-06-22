package ezenweb.service;

import ezenweb.domain.board.BoardEntity;
import ezenweb.domain.board.BoardRepository;
import ezenweb.domain.board.CategoryEntity;
import ezenweb.domain.board.CategotyRepository;
import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.dto.BoardDto;
import ezenweb.dto.LoginDto;
import ezenweb.dto.MemberDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategotyRepository categotyRepository;

    @Transactional
    // 1. C[인수 dto]
    public boolean save(BoardDto boardDto){

        LoginDto loginDto = (LoginDto)request.getSession().getAttribute("login");
        if(loginDto != null){ // 로그인이 되어있으면 : Optional클래스[null 저장]
            // 2. 로그인된 회원의 엔티티 찾기
            Optional<MemberEntity> optionalMember = memberRepository.findById(loginDto.getMno());
                // findByid(pk키) => 반환타입
            if(optionalMember.isPresent()){ // null이 아니면
                // Optional클래스내 메소드 : .isPresent() : null이 아니면
                // 3. Dto -> entity
                System.out.println(boardDto.getCategory());
                        // 만약에 기존에 있는 카테고리 이면
                        boolean sw = false;
                        int cno = 0;
                        List<CategoryEntity> categoryEntityList = categotyRepository.findAll();
                        for(CategoryEntity entity : categoryEntityList){
                            if(entity.getCname().equals(boardDto.getCategory())){
                                sw = true;
                                cno = entity.getCno();
                            }
                        }
                CategoryEntity categoryEntity = null;
                        if(!sw){
                            // 1. 카테고리 생성
                            categoryEntity = CategoryEntity.builder()
                                    .cname(boardDto.getCategory())
                                    .build();

                            categotyRepository.save(categoryEntity);
                        }else{
                            categoryEntity = categotyRepository.findById(cno).get();
                        }

                BoardEntity boardEntity = boardRepository.save(boardDto.toentity());

                // 4. 작성자 추가
                boardEntity.setMemberEntity(optionalMember.get());
                boardEntity.setCategoryEntity(categoryEntity);

                    // 카테고리 엔티티에 게시물 연견
                    categoryEntity.getBoardEntityList().add(boardEntity);
                    // 회원 엔티티에 게시물 연결
                    optionalMember.get().getBoardEntityList().add(boardEntity);

                // 5. 반환
                return true;
            }
            // 1. dto -> entity

            // save(엔티티) => 반환타입 : 저장된 엔티티
            // 2. 작성 회원 엔티티 추가
        }else{ // 로그인이 안 되어 있는경우
            return false;
        }
        return false;
    }
    // 2. R[인수 : x 바환 : 1.json 2. map]
    public JSONObject getboardlist(int cno, String key , String keyword , int page){
        JSONArray jsonArray = new JSONArray();

        JSONObject object = new JSONObject();

        Page<BoardEntity> boardEntitys = null; // 선언만

        // Pageable : 페이지처리 관련 인터페이스
        // PageRequest : 페이징처리 관련 클래스
                // PageRequest.of(page,size) : 페이징처리 설정
                    // page = "현재 페이지" [0부터 시작]
                    // size = "현재페이지 보여줄 게시물 수"
                    // sort = "정렬기준" [ Sort.by(Sort.Direction.DESC)) , "정렬필드명" ]
                        // sort문제점 : 정렬 필드명에 _ 인식 불가능! --> sql처리
        Pageable pageable = PageRequest.of(page , 5 , Sort.by(Sort.Direction.DESC , "bno")); // sql : limit와 동일한 기능처리

        // 필드에 따른 검색 기능
        if(key.equals("btitle")){
            boardEntitys = boardRepository.findBybtitle(cno , keyword, pageable);
        }else if(key.equals("bcontent")){
            boardEntitys = boardRepository.findBybcontent(cno , keyword, pageable);
        }else if(key.equals("mid")){
            // 입력받은 mid -> [mno]엔티티 변환
                // 만약에 없는 아이디를 검색했으면
            Optional<MemberEntity> optionalMember = memberRepository.findBymid(keyword);
            if(optionalMember.isPresent()){ // .isPresent() : Optional 이 null 아니면
                MemberEntity memberEntity = optionalMember.get(); // 엔티티 추출
                boardEntitys = boardRepository.findBymno(cno , memberEntity, pageable); // 찾은 회원 엔티티를 -> 인수로 전달
            }else{ // null 이면
//                return ; // 결과가 없으면
            }
        }else{ // 검색이 없으면
            boardEntitys = boardRepository.findBybtitle(cno, keyword , pageable);
        }

        // 페이지에 표시할 총 페이징 버튼 개수
        int btncount = 5;
        // 시작번호 버튼의 번호 [현재 페이지 / 표시할 버튼 수] * btncount + 1
        int startbtn = (page / btncount) * btncount + 1;
        /*
3/5 -> 0 *5 +1->1

   7/5 -> 1 * 5 -> 5+1 ->  6

         */

        // 끝 번호 번튼의 번호 [시작 버튼 + 표시할 버튼수 -1]
        int endbtn = startbtn + btncount -1;
    /*
    1+5-1->5

    6+5-1->10

     */


            // 만약에 끝 번호가 마지막 페이지보다 크면 끝 번호는 마지막 페이지 번호로 사용
            if(endbtn > boardEntitys.getTotalPages()) endbtn = boardEntitys.getTotalPages();

        // 엔티티 반환타입을 리스트가 아닌 페이지 인터페이스 할 경우에
//        System.out.println("검색된 총 게시물 수 : " + boardEntitys.getTotalElements());
//        System.out.println("검색된 총 페이지 수 : " + boardEntitys.getTotalPages());
//        System.out.println("검색된 게시물 정보 : " + boardEntitys.getContent());
//        System.out.println("현재 페이지 수 : " + boardEntitys.getNumber());
//        System.out.println("현재 페이지 게시물수 : " + boardEntitys.getNumberOfElements());
//        System.out.println("현재 페이지 첫 페이지 여부확인 : " + boardEntitys.isFirst());
//        System.out.println("현재 페이지 마지막 페이지 여부확인 : " + boardEntitys.isLast());

        // data :  모든 엔티티 -> JSON 변환
        for(BoardEntity entity : boardEntitys){
                JSONObject jsonObject = new JSONObject();
                    jsonObject.put("bno", entity.getBno());
                    jsonObject.put("btitle", entity.getBtitle());
                    jsonObject.put("bindate", entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:mm:ss")));
                    jsonObject.put("bview", entity.getBview());
                    jsonObject.put("blike", entity.getBlike());
                    jsonObject.put("mid", entity.getMemberEntity().getMid());
                jsonArray.put(jsonObject);
        }

        //js 보낼 jsonobject
        object.put("startbtn" , startbtn ); // 페이징 시작 버튼
        object.put("endbtn" , endbtn); // 페이징 끝 버튼
        object.put("totalpages" , boardEntitys.getTotalPages()); // 전체 페이지수
        object.put("data" , jsonArray); // 리스트를 추가

        return object;
    }
    @Transactional
    // 2 R : 개별조회[게시물 번호]
    public JSONObject getboard(int bno){

        // 조회수 증가처리 [기준 : ip / 24시간]
        String ip = request.getRemoteAddr(); // 사용자의 ip 가져오기

        Optional<BoardEntity> optional = boardRepository.findById(bno);
        BoardEntity entity = optional.get();
            // ip와 bno합쳐서 세션(서버내 저장소) 부여
        // 세션 호출
        Object com = request.getSession().getAttribute(ip+bno);
        if(com == null){ // 만약에 세션이 있으면
            // ip와 bno 합쳐서 세션(서버내 저장소) 부여
            request.getSession().setAttribute(ip+bno , 1);
            request.getSession().setMaxInactiveInterval(60*60*24); // 세션 허용 시간[초단위]
            // 조회수 증가처리
            entity.setBview(entity.getBview()+1);
        }


        JSONObject object = new JSONObject();
        object.put("bno" , entity.getBno());
        object.put("btitle" , entity.getBtitle());
        object.put("bcontent" , entity.getBcontent());
        object.put("bview" , entity.getBview());
        object.put("blike" , entity.getBlike());
        object.put("bindate" , entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:mm:ss")));
        object.put("bmodate" , entity.getModifiedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:mm:ss")));
        object.put("mid" , entity.getMemberEntity().getMid());
        return object;



    }
    @Transactional
    // 3. U [인수 : 게시물 번호, 수정할 내용]
    public boolean update(BoardDto boardDto){
        Optional<BoardEntity> optionalBoard = boardRepository.findById(boardDto.getBno());
        BoardEntity boardEntity = optionalBoard.get();
        boardEntity.setBtitle(boardDto.getBtitle());
        boardEntity.setBcontent(boardDto.getBcontent());
        return true;
    }
    @Transactional
    // 4. D [인수 : 게시물 번호]
    public boolean delete(int bno){
       BoardEntity boardEntity = boardRepository.findById(bno).get();
       boardRepository.delete(boardEntity);
       return true;

    }

    // 5. 카테고리 호출 메소드
     public JSONArray getcategotylist(){
        List<CategoryEntity> categoryEntityList = categotyRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for(CategoryEntity entity : categoryEntityList){
            JSONObject object = new JSONObject();
            object.put("cno" , entity.getCno());
            object.put("cname" , entity.getCname());
            jsonArray.put(object);
        }
        return jsonArray;
     }


}
