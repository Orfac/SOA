import React, { useState } from 'react'
import { createShip } from '../../api/ship-api';

const CreateShip : React.FC = () => {

    const [createId, setCreateId] = useState(0)
    const [createName, setCreateName] = useState("");
    
    const changeCreateIdShip = async (e : React.FormEvent<HTMLInputElement>) => {
        setCreateId(Number(e.currentTarget.value));
    }

    const changeCreateNameShip = async (e : React.FormEvent<HTMLInputElement>) => {
        setCreateName(e.currentTarget.value);
    }

    const requestCreateShip = async () => {
        await createShip(createId, createName);
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
            </div>
        </div>
    )
}

export default CreateShip
