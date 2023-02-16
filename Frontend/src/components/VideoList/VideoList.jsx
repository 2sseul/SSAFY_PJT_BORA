import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
// import classes from "./VideoList.modeul.scss";
import "./VideoList.scss";

const VideoList = () => {
  const navigate = useNavigate();
  const [category, setCategory] = useState();
  const [mood, setMood] = useState([]);
  const [sortBy, setSortBy] = useState();
  const [check, setCheck] = useState(false);

  const changeCategoryHandler = () => {
    setCategory();
  };

  const changeMoodHandler = () => {
    setMood({
      ...mood,
    });
  };

  const changeSortByHandler = () => {
    setSortBy();
  };

  const checkHandler = () => {
    if (check === false) {
      setCheck(true);
    } else {
      setCheck(false);
    }
  };

  //-------------- 버튼 선택시 렌더링 ---------------
  useEffect(() => {
    const API_URL = `https://i8b301.p.ssafy.io/api/main/live-broad/`;
    axios({
      url: API_URL,
      method: "GET",
      params: { category, mood, sortBy },
    })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [category, mood, sortBy]);
  return (
    <>
      <div class="container">
        <form>
          <label>
            <input type="radio" name="category" />
            <span>노래</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>춤</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>책</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>더빙</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>교육</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>ASMR</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>상담</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>뉴스</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>엔터</span>
          </label>
          <label>
            <input type="radio" name="category" />
            <span>인터뷰</span>
          </label>
        </form>
      </div>
      <div>
        <select>
          <option>최신순</option>
          <option>인기순</option>
          <option>팔로워순</option>
        </select>
      </div>
      <div></div>
    </>
  );
};

export default VideoList;