import React, {useState} from 'react';
import SpaceMarine from "../SpaceMarine";
import Config from '../../api/Config.json';
import './FormStyle.css';
import {getMarines, save} from "../../api/marines-api";
import {handleXml} from "../../utils/MarineXmlExtensions";
import {ISpaceMarine} from "../../model/ISpaceMarine";


const MarineList: React.FC = () => {
    const url = Config.Url + "/marines";
    const [marines, setMarines] = useState<ISpaceMarine[]>([]);
    const [pageSize, setPageSize] = useState("");
    const [pageNumber, setPageNumber] = useState("");
    const [message, setMessage] = useState("");
    const [isError, setIsError] = useState(false);
    const [isAddOpen, setAddOpen] = useState(false);
    const [addValue, setAddValue] = useState("");

    const [filter, setFilter] = useState("");
    const [sort, setSort] = useState("");


    const renderMarines = (): JSX.Element[] => {
        return marines.map((value: ISpaceMarine, index: number): JSX.Element =>
            <SpaceMarine key={index} marine={value} callForUpdate={reUpdateMarines}/>
        );
    }

    const reUpdateMarines = async () => {
        await updateMarines();
    }

    function buildUrl() {
        let finalUrl = url + "?";
        if (pageSize && pageNumber) {
            finalUrl += "&pageNumber=" + pageNumber + "&pageSize=" + pageSize
        }
        if (filter) {
            finalUrl += "&" + filter;
        }
        if (sort) {
            finalUrl += "&sortBy=" + sort;
        }
        return finalUrl;
    }

    const updateMarines = async () => {
        setMessage("");
        setIsError(false);
        setAddOpen(false);

        const url = buildUrl()
        const updatedMarinesResponse = await getMarines(url);
        let updatedMarines: Array<ISpaceMarine> = [];
        const responseText = await updatedMarinesResponse.text();
        if (updatedMarinesResponse.status === 200) {
            updatedMarines = handleXml(responseText);
            if (updatedMarines.length < 1) {
                onError("There are 0 space marines");
            }
        } else {
            onError(message);
        }

        setMarines(updatedMarines);
    }
    const onError = (message: string) => {
        setIsError(true);
        setMessage(message);
    }

    const inputPageSize = (event: React.FormEvent<HTMLInputElement>) => setPageSize(event.currentTarget.value);
    const inputPageNumber = (event: React.FormEvent<HTMLInputElement>) => setPageNumber(event.currentTarget.value);
    const inputSort = (event: React.FormEvent<HTMLInputElement>) => setSort(event.currentTarget.value);
    const inputFilter = (event: React.FormEvent<HTMLInputElement>) => setFilter(event.currentTarget.value);
    const updateNew = (event: React.FormEvent<HTMLTextAreaElement>) => setAddValue(event.currentTarget.value);

    const openAdd = () => {
        setAddOpen(!isAddOpen);

        setPageNumber("");
        setPageSize("");
        setSort("");
        setFilter("");
        setIsError(false);
        setMessage("");
    };
    const sendNew = async () => {
        setMessage("");
        setIsError(false);
        const rawResponse = await save(addValue);
        if (rawResponse.status === 201) {
            setMessage("New marine added!");
        } else {
            if (rawResponse.status === 400) {
                setIsError(true);
                const response = await rawResponse.text();
                setMessage(response ? response : "Cannot add this marine, please rewrite it in xml style");
            }
        }
    }

    return (
        <div className="form-container">
            <h2>
                Space marines
            </h2>
            <div>
                <button className="cool-button" onClick={updateMarines}>Get marines</button>
                <button className="cool-button" onClick={openAdd}>{isAddOpen ? "Close marine" : "Add marine"}</button>
                {isAddOpen ? "" : <div>
                    <div>
                        <div>
                            Page size
                            <input type="text" onChange={inputPageSize} placeholder="1"/>
                        </div>
                        <div>
                            Page number
                            <input type="text" onChange={inputPageNumber} placeholder="1"/>
                        </div>
                    </div>
                    <div>
                        Filter by
                        <input type="text" onChange={inputFilter} placeholder="name=vasya&id=1"/>
                    </div>
                    <div>
                        Sort by
                        <input type="text" onChange={inputSort} placeholder="name,id"/>
                    </div>
                </div>}
            </div>


            <div>
                {isAddOpen ? <div>
                    <h2>XML view</h2>

                    <textarea cols={60} rows={20} onChange={updateNew}/>
                    <div>
                        <button className="cool-button" onClick={sendNew}>
                            Load new marine
                        </button>
                    </div>
                </div> : renderMarines()}
            </div>

            {message ? <div className={isError ? "error-message" : "success"}>{message}</div> : ""}


        </div>
    );

}

export default MarineList;
