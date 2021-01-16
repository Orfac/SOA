import React, {useState} from 'react';
import SpaceMarine from "../SpaceMarine";
import Config from "../../api/Config.json";
import { handleXml } from '../../utils/MarineXmlExtensions';
import { ISpaceMarine } from '../../model/ISpaceMarine';

const Features = () => {

    const [summaryHealth, setSummaryHealth] = useState("");
    const [Category, setCategory] = useState("");
    const [marines, setMarines] = useState<ISpaceMarine[]>([]);
    const [healthAmount, setHealthAmount] = useState("");
    const[message,setMessage] = useState("");

    const getSummaryHealth = async () => {
        const url = Config.Url + "/health";
        const response =  await fetch(url, {
            method: "GET"
        });
        setSummaryHealth(await response.text());
    }
    const renderMarines = () => {
        return marines.map(
            // eslint-disable-next-line react/jsx-key
            (marine) => <SpaceMarine marine={marine} callForUpdate={getMarines} />
        )
    }

    const getMarines = async () => {
        const url = Config.Url + "/compare/" + healthAmount;
        const response =  await fetch(url, {
            method: "GET"
        });
        if (response.status === 200){
            let updatedMarines : Array<ISpaceMarine> = [];
            const responseText = await response.text();
            if (response.status === 200) {
                updatedMarines = await handleXml(responseText);
            }

            setMarines(updatedMarines);
        }
    }

    const changeHealth = (event : React.FormEvent<HTMLInputElement>) => {
        setHealthAmount(event.currentTarget.value);
    }
    function changeCategory(event : React.FormEvent<HTMLInputElement>) {
        setCategory(event.currentTarget.value);
    }

    async function deleteMarine() {
        const url = Config.Url + "/random/" + Category;
        const response =  await fetch(url, {
            method: "DELETE"
        });
        setMessage(await response.text())

    }

    return (
        <div>
            <h2>
                Features
            </h2>

            <div className="form-container">
                <h4 >All health</h4>
                <div>
                    <button className="cool-button" onClick={getSummaryHealth}>Get summary health</button>
                    <div>Summary health = {summaryHealth}</div>
                </div>
            </div>

            <div>
                <div className="form-container">
                    <h4 >Delete random by category</h4>
                    <div>
                        <input type="text" onChange={changeCategory}/>
                        <button className="cool-button delete" onClick={deleteMarine}>Delete random</button>
                        <div>{message? message : ""}</div>
                    </div>
                </div>
            </div>

            <div className="form-container">
                <h4 >Marines with health greater</h4>
                <div>
                    <input type="text" onChange={changeHealth}/>
                    <button className="cool-button" onClick={getMarines}>Get healthier marines</button>
                    {marines !== [] ? renderMarines() : ""}
                </div>
            </div>
        </div>
    );

}

export default Features;
