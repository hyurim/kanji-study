import React, { useState } from "react";
import styled from "styled-components";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
`;

const LoginForm = styled.form`
  width: 400px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const InputBox = styled.div`
  font-family: "Pretendard-Regular", serif;
  width: 100%;
  border: 0.3px solid #a69b9b;
  border-radius: 30px;
  padding: 0 10px;
  display: flex;
  align-items: center;
  background-color: #ffffff;
  color: #a69b9b;
  font-size: 15px;
`;

const Label = styled.span`
  padding-right: 15px;
  padding-left: 15px;
  font-size: 15px;
  color: #a69b9b;
  white-space: nowrap;
`;

const Divider = styled.span`
  color: #a69b9b;
  margin: 0 8px;
`;

const Input = styled.input`
  font-family: "Pretendard-Regular", serif;
  flex: 1;               /* 가로 너비 자동 */
  height: 50px;
  font-size: 16px;
  border: none;
  border-radius: 30px;
  background: transparent;
  outline: none;
`;

const LoginButton = styled.button`
  font-family: "Pretendard-Regular", serif;
  width: 420px;
  height: 50px;
  background-color: #ffb770;
  border: none;
  border-radius: 30px;
  color: white;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 20px;
`;

const Links = styled.div`
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  border-bottom: 1px solid #a69b9b;
  line-height: 3;
`;

const StyledLink = styled.a`
  font-family: "Pretendard-Regular", serif;
  text-decoration: none;
  color: #999;
  font-size: 14px;
`;

const Separator = styled.span`
  font-family: "Pretendard-Regular", serif;
  color: #a69b9b;
  margin: 0 10px;
`;

const Login = () => {
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    const loginData = { userId, password };

    try {
      const response = await fetch("/web/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(loginData),
        credentials: "include",
      });

      if (response.ok) {
        const data = await response.json();
        const token = data.access_token;
        localStorage.setItem("token", token);
        console.log("로그인 성공, JWT:", token);
        window.location.href = "/"; 
      } else {
        console.error("로그인 실패");
      }
    } catch (error) {
      console.error("서버 오류 발생", error);
    }
  };

  return (
    <Container>
      <LoginForm onSubmit={handleLogin}>
        <InputBox>
          <Label>아이디</Label>
          <Divider>|</Divider>
          <Input
            type="text"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            placeholder="아이디 입력"
            autoComplete="username"
          />
        </InputBox>

        <InputBox>
          <Label>비밀번호</Label>
          <Divider>|</Divider>
          <Input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="비밀번호 입력"
            autoComplete="current-password"
          />
        </InputBox>

        <LoginButton type="submit">로그인</LoginButton>
      </LoginForm>

      <Links>
        <StyledLink href="./FindID">아이디/비밀번호 찾기</StyledLink>
        <Separator> | </Separator>
        <StyledLink href="/signUp">회원가입</StyledLink>
      </Links>
    </Container>
  );
};

export default Login;