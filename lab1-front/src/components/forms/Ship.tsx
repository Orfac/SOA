import React, {useState} from 'react';
import Config from "../../api/Config.json";
import { loadNewMarine } from '../../api/ship-api';
import { IShip } from '../../model/IShip';
import CreateShip from '../ship/CreateShip';

const Ship : React.FC = () => {

    const [loadId, setLoadId] = useState(0);
    const [loadMarineId, setLoadMarineId] = useState(0);
    const [buildedShips, setBuildedShips] = useState([]);
    const [message, setMessage] = useState("");



    async function showShip() {
        const url = `${Config.Extra}/get`;
        const response = await fetch(url, {
            method: "GET"
        });
        const json = await response.json();
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
            // eslint-disable-next-line react/jsx-key
            (ship : IShip) => <div className="marine-container ship">
                <h4>Ship {ship.id} with name {ship.name}</h4>
                <div>Marines: {ship.marineIds ? ship.marineIds : "are missing"}</div>
            </div>
        )
    }

    return (
        <div className="form-container">
            <CreateShip/>
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


