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

    const getSummaryHealth = async () => {
        let url = Config.Url + "/health";
        let response =  await fetch(url, {
            method: "GET"
        });
        setSummaryHealth(await response.text());
    }
    const renderMarines = () => {
        return marines.map(
            (marine) => <SpaceMarine marine={marine} callForUpdate={getMarines}/>
        )
    }

    const getMarines = async () => {
        let url = Config.Url + "/compare/" + healthAmount;
        let response =  await fetch(url, {
            method: "GET"
        });
        if (response.status === 200){
            let updatedMarines : Array<ISpaceMarine> = [];
            let responseText = await response.text();
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
        let url = Config.Url + "/random/" + Category;
        let response =  await fetch(url, {
            method: "DELETE"
        });
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
                    <h4 >Delete random</h4>
                    <div>
                        <input type="text" onChange={changeCategory}/>
                        <button className="cool-button delete" onClick={deleteMarine}>Delete random</button>
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
