import React, {useState} from 'react';
import Config from "../../api/Config.json";
import { loadNewMarine } from '../../api/ship-api';
import { IShip } from '../../model/IShip';
import CreateShip from '../ship/CreateShip';
import LoadMarine from "../ship/LoadMarine";

const Ship : React.FC = () => {

    const [buildedShips, setBuildedShips] = useState([]);

    async function showShips() {
        const url = `${Config.Extra}/get`;
        const response = await fetch(url, {
            method: "GET"
        });
        const json = await response.json();
        setBuildedShips(json);
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
            <LoadMarine/>
            <div className="form-container">
                <h2>Show ships</h2>
                <div>
                    <button className="cool-button" onClick={showShips}>Show</button>
                </div>
                {buildedShips.length > 0 ? <div>{renderShips()}</div> : <div>There are no ships in shipyard</div>}
            </div>
        </div>
    );
}

export default Ship;


