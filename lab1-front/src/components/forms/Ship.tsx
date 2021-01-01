import React, {useState} from 'react';
import Config from "../../api/Config.json";
import { createShip, loadNewMarine } from '../../api/ship-api';
import { IShip } from '../../model/IShip';

const Ship : React.FC = () => {
    const [createId, setCreateId] = useState(0)
    const [createName, setCreateName] = useState("");
    const [loadId, setLoadId] = useState(0);
    const [loadMarineId, setLoadMarineId] = useState(0);
    const [buildedShips, setBuildedShips] = useState([]);
    const [message, setMessage] = useState("");

    const changeCreateIdShip = async (e : React.FormEvent<HTMLInputElement>) => {
        setCreateId(Number(e.currentTarget.value));
    }

    const changeCreateNameShip = async (e : React.FormEvent<HTMLInputElement>) => {
        setCreateName(e.currentTarget.value);
    }

    const requestCreateShip = async () => {
        await createShip(createId, createName);
    }

    async function showShip() {
        let url = `${Config.Extra}/get`;
        let response = await fetch(url, {
            method: "GET"
        });
        let json = await response.json();
        setBuildedShips(json);
    }

    async function loadNew() {
         await loadNewMarine(loadId, loadMarineId);
    }

    function changeLoadMarineIdShip(e : React.FormEvent<HTMLInputElement>) {
        setLoadMarineId(Number(e.currentTarget.value));
    }

    function changeLoadIdShip(e : React.FormEvent<HTMLInputElement>) {
        setLoadId(Number(e.currentTarget.value));
    }

    const renderShips = () => {
        return buildedShips.map(
            (ship : IShip) => <div className="marine-container ship">
                <h4>Ship {ship.id} with name {ship.name}</h4>
                <div>Marines: {ship.marineIds ? ship.marineIds : "are missing"}</div>
            </div>
        )
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
                    <button className="cool-button" onClick={requestCreateShip}>CREATE</button>
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
                {buildedShips.length > 0 ? <div>{renderShips()}</div> : ""}
            </div>
        </div>
    );
}

export default Ship;


