import React, { useState } from 'react'
import { createShip } from '../../api/ship-api';

const CreateShip : React.FC = () => {

    const [createId, setCreateId] = useState(0)
    const [createName, setCreateName] = useState("");
    const [message, setMessage] = useState("");

    const changeCreateIdShip = async (e : React.FormEvent<HTMLInputElement>) => {
        setCreateId(Number(e.currentTarget.value));
    }

    const changeCreateNameShip = async (e : React.FormEvent<HTMLInputElement>) => {
        setCreateName(e.currentTarget.value);
    }

    const requestCreateShip = async () => {
        const apiResponse = await createShip(createId, createName);
        if (apiResponse.status !== 200){
            setMessage(apiResponse.errorMessage ? apiResponse.errorMessage : "")
        } else {
            setMessage(`Ship with id ${createId} and name ${createName} was successfully created`)
        }
    }
    return (
        <div>
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
                {message ? <div>{message}</div> : ""}
            </div>
        </div>
    )
}

export default CreateShip
