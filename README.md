# 1jojo

1jojo 프로젝트 실행 시작을 위한 세팅.

1. WebContent/SQL/crowling.exrd 실행해서 exrd 탭에 포워드 엔지니어링 누른다. DB ID와 비번은 totoro와 totoro123이다.
2. 같은 폴더에 seq_collection.sql에 있는 구문을 모두 실행시켜 시퀀스를 생성해둔다.
3. 1jojo 프로젝트 최상단 다이나믹 프로젝트 아이콘에서 오른쪽 버튼을 눌러서 run as 로 들어가 server로 실행한다.

aws 호스팅 이후 세팅
1. 자바, 톰캣(포트번호 80), 오라클express 버전 설치후 톰캣 폴더의 root에 war파일 두고 '여기에 압축풀기'.
2. collection이란 sql 파일 run으로 실행하거나, sqldeveloper를 켠 후 사용자 추가>>sid는 xe, 호스트이름에 IP주소, 포트는 안건드려도 되고, 접속이름은 임의로 짓는다, 사용자 이름과 비밀번호는 원격지에서 만든걸로(미리 totoro만들고 권한 부여해야함 귀찮으면 grant 문으로 dba줄것.). 이후 sql문으로 필요한 db 생성
3. 'ip주소/'로 그냥 들어가면 web.xml 설정에 의해 알아서 ip주소/member/main.jsp로 들어간다. 보면 알겠지만 이제 프로젝트명 대신 ip주소가 통용된다.
4. jsp에 있는 /1jojo를 <%=request.getContextPath() %>로 모두 바꿔야 한다.
5. 자바 단의 setpath에서 ./패키지/파일 들어가는 형식은 무난하게 먹힌다.  /****.**도 무난히 인식.
6. 자바스크립트 단의 href는 /****.** 로 해도 무난하게 서블릿에서 인식한다. 뒤에 ?**=**&**=**같이 붙어도 무난하게 인식.


