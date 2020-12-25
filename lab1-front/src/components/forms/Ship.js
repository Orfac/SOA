import React, {useState} from 'react';
import Config from "../../api/Config.json";

const Ship = () => {
    const [createId, setCreateId] = useState("")
    const [createName, setCreateName] = useState("");
    const [loadId, setLoadId] = useState("");
    const [loadMarineId, setLoadMarineId] = useState("");
    const [buildedShips, setBuildedShips] = useState([""]);

     const changeCreateIdShip = async (e) => {
        setCreateId(e.target.value);
    }

     const changeCreateNameShip = async (e) => {
        setCreateName(e.target.value);
    }

    const createShip = async () => {
        let url = `${Config.Extra}/create/${createId}/${createName}`;
        let response = await fetch(url ,{
            method: "POST"
        });
        return response;
    }

    async function showShip() {
        let url = `${Config.Extra}/get`;
        let response = await fetch(url ,{
            method: "GET"
        });
        let json = await response.json();
        setBuildedShips(json);
    }

    async function loadNew() {
        let url = `${Config.Extra}/${loadId}/load/${loadMarineId}`;
        let response = await fetch(url ,{
            method: "POST"
        });
        return response;
    }

    function changeLoadMarineIdShip(e) {
        setLoadMarineId(e.target.value);
    }

    function changeLoadIdShip(e) {
        setLoadId(e.target.value);
    }

    return (
        <div className="form-container">
            <div>
                <h2>Create new ship</h2>
                <div>
                    Id: <input type="text" onChange={changeCreateIdShip}/>
                </div>
                <div>
                    Name: <input type="text" onChange={changeCreateNameShip}/>
                </div>
                <div>
                    <button className="cool-button" onClick={createShip}>CREATE</button>
                </div>
            </div>
            <div className="form-container">
                <h2>Load marine</h2>
                <div>
                    Id: <input type="text" onChange={changeLoadIdShip}/>
                </div>
                <div>
                    MarineId: <input type="text" onChange={changeLoadMarineIdShip}/>
                </div>
                <div>
                    <button className="cool-button" onClick={loadNew}>Load</button>
                </div>
            </div>
            <div className="form-container">
                <h2>Show ships</h2>
                <div>
                    <button className="cool-button" onClick={showShip}>Show</button>
                </div>
                {buildedShips ? <div>{buildedShips}</div> : ""}
            </div>
        </div>
    );
}

export default Ship;
