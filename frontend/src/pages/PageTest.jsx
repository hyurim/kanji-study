import { useEffect } from "react";
import {  fetchKunyomiList, fetchKunSentenceList, fetchOnyomiList, fetchOnSentenceList  } from "../lib/api";

const PageTest = () => {
	useEffect(() => {
		fetchKunyomiList()
		  .then(data => console.log("훈독 단어:", data))
		  .catch(err => console.error(err));
	  }, []);
	  useEffect(() => {
		fetchKunSentenceList()
		  .then(data => console.log("훈독 예문:", data))
		  .catch(err => console.error(err));
	  }, []);
	  useEffect(() => {
		fetchOnyomiList()
		  .then(data => console.log("음독 단어:", data))
		  .catch(err => console.error(err));
	  }, []);
	  useEffect(() => {
		fetchOnSentenceList()
		  .then(data => console.log("음독 예문:", data))
		  .catch(err => console.error(err));
	  }, []);
	
	  return <div>콘솔</div>;
}

export default PageTest;