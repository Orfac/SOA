import React, { useState } from 'react'
import {loadNewMarine} from '../../api/ship-api';

const LoadMarine : React.FC = () => {

    const [message, setMessage] = useState("");
    const [loadId, setLoadId] = useState(0);
    const [loadMarineId, setLoadMarineId] = useState(0);

    async function loadNew() {
        const apiResponse = await loadNewMarine(loadId, loadMarineId);
        if (apiResponse.status !== 200){
            setMessage(apiResponse.errorMessage ? apiResponse.errorMessage : "")
        } else {
            setMessage(`Marine with id ${loadMarineId} was successfully created to ship with id ${loadId}`)
        }
    }

    function changeLoadMarineIdShip(e : React.FormEvent<HTMLInputElement>) {
        setLoadMarineId(Number(e.currentTarget.value));
    }

    function changeLoadIdShip(e : React.FormEvent<HTMLInputElement>) {
        setLoadId(Number(e.currentTarget.value));
    }

    return (
        <div>
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
                {message ? <div>{message}</div> : ""}
            </div>
        </div>
    )
}

export default LoadMarine
