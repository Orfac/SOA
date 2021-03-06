import React, {useState} from 'react';
import './SpaceMarine.scss';
import {deleteById, updateById} from "../api/marines-api";
import {ISpaceMarine} from "../model/ISpaceMarine";

interface IProps {
    marine: ISpaceMarine,
    callForUpdate: () => Promise<void>
}

const SpaceMarine: React.FC<IProps> = (props: IProps) => {
    const marine = props.marine
    const callForUpdate = props.callForUpdate

    const chapter = marine.chapter;

    const [isEditOpen, setEditOpen] = useState(false);
    const [updateValue, setUpdateValue] = useState(marine.xmlText);
    const [error, setError] = useState("");

    function updateXml(event: React.ChangeEvent<HTMLTextAreaElement>) {
        setUpdateValue(event.currentTarget.value);
    }

    const sendDelete = async () => {
        await deleteById(marine.id)
        callForUpdate();
    }

    const editOpen = () => {
        setEditOpen(!isEditOpen);
    }

    const sendUpdate = async () => {
        setEditOpen(!isEditOpen);
        setError("");
        const response = await updateById(marine.id, updateValue);
        if (response.status >= 400) {
            setError("Error occurred: " + await response.text());
        } else {
            callForUpdate();
        }

    };

    const renderHealthMessage = marine.health ? `health=${marine.health}` : "";
    const renderHeartCountMessage = marine.heartCount ? `heartCount=${marine.heartCount}` : "";
    return (
        <div className="marine-container">
            <div className="header">
                <button className="cool-button delete" onClick={sendDelete}>
                    Delete
                </button>
                <div>
                    <h2 className="main-header">{marine.name} №{marine.id}</h2>
                </div>


                <button className="cool-button edit" onClick={editOpen}>
                    Edit
                </button>


            </div>
            {isEditOpen ?
                <div className="xml-edit">
                    <h2>XML view</h2>

                    <textarea cols={60} rows={20} onChange={updateXml} defaultValue={updateValue}/>
                    <button className="cool-button" onClick={sendUpdate}>
                        Update {marine.name}
                    </button>
                </div> :
                <div>

                    <div><b>{marine.category}</b> with <b>{marine.meleeWeapon}</b></div>
                    <div>Creation date: {marine.creationDate}</div>
                    <div>Coordinates: {marine.coordinates.x ? `x=${marine.coordinates.x}` : ""} y={marine.coordinates.y} </div>
                    <div>Health information: {renderHealthMessage} {renderHeartCountMessage}</div>
                    {chapter !== null ? <div className="chapter-info">
                            <h2>Chapter</h2>
                            <div>chapter = {chapter.name}</div>
                            <div>parent legion = {chapter.parentLegion}</div>
                            <div>marines count = {chapter.marinesCount}</div>
                            <div>world = {chapter.world}</div>
                        </div> :
                        <div className="chapter-info">
                            Chapter is missing
                        </div>}
                </div>
            }

            <div className="little-error-message">
                {error}
            </div>

        </div>
    );
}

export default SpaceMarine;
