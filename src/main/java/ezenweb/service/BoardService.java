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
    public JSONArray getboardlist(int cno){
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardEntitys = boardRepository.findAll();
        // 모든 엔티티 -> JSON 변환
        for(BoardEntity entity : boardEntitys){
            if(entity.getCategoryEntity().getCno() == cno) {
                JSONObject object = new JSONObject();
                object.put("bno", entity.getBno());
                object.put("btitle", entity.getBtitle());
                object.put("bindate", entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:mm:ss")));
                object.put("bview", entity.getBview());
                object.put("blike", entity.getBlike());
                object.put("mid", entity.getMemberEntity().getMid());
                jsonArray.put(object);
            }
        }
        return jsonArray;
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
