import './App.scss';
import React, {useState} from "react";

import Features from "./components/forms/Features";
import MarineList from './components/forms/MarineList';
import Ship from "./components/forms/Ship";


const App: React.FC = () => {

  const [stateNumber, setStateNumber] = useState(0);

  function select(value: string) {
    switch (value) {
      case "main":
        setStateNumber(0);
        break;
      case "features":
        setStateNumber(1);
        break;
      case "ship":
        setStateNumber(2);
        break;
      default:
        break;
    }
    return undefined;
  }

  return (
      <div className="App">
        <div className="mode-selection">
          <div className="mode" onClick={() => select("main")}>Main page</div>
          <div className="mode" onClick={() => select("features")}>Features</div>
          <div className="mode mode-dlc" onClick={() => select("ship")}>Ship DLC</div>
        </div>
        <div>
          {stateNumber === 0 ? <MarineList/> : ""}
          {stateNumber === 1 ? <Features/> : ""}
          {stateNumber === 2 ? <Ship/> : ""}
        </div>


      </div>
  );
}

export default App;
