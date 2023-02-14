import React from "react";

import "./GoButton.scss";

const GoButton = (props) => {
  return (
    <div id="container">
      <button id="goButton" className="learn-more">
        <span className="circle" aria-hidden="true">
          <span className="icon arrow"></span>
        </span>
        <span className="button-text">{props.value}</span>
      </button>
      <button id="goButton" class="learn-more">
        <span class="circle" aria-hidden="true">
          <span class="icon arrow"></span>
        </span>
        <span class="button-text">{props.value}</span>
      </button>
    </div>
  );
};

export default GoButton;
