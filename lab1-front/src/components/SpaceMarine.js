import React, {useState} from 'react';
import './SpaceMarine.scss';
import {deleteById, updateById} from "../api/utils";

const SpaceMarine = ({marine, callForUpdate}) => {
    let chapter = marine.chapter;

    const [isEditOpen, setEditOpen] = useState(false);
    const [updateValue, setUpdateValue] = useState(marine.xmlText);
    const [error, setError] = useState("");

    function updateXml(event) {
        setUpdateValue(event.target.value);
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
        let response = await updateById(marine.id, updateValue);
        if (response.status >= 400) {
            setError("Error occurred: " + await response.text());
        } else {
            callForUpdate();
        }

    };

    return (
        <div className="marine-container">
            <div className="header">
                <button className="cool-button delete" onClick={sendDelete}>
                    Delete
                </button>
                <h2 className="main-header">{marine.name} №{marine.id}</h2>

                <button className="cool-button edit" onClick={editOpen}>
                    Edit
                </button>


            </div>
            {isEditOpen ?
                <div className="xml-edit">
                    <h2>XML view</h2>

                    <textarea cols="60" rows="20" onChange={updateXml} defaultValue={updateValue}/>
                    <button className="cool-button" onClick={sendUpdate}>
                        Update {marine.name}
                    </button>
                </div> :
                <div className="info-container">
                    <div className="chapter-info">
                        <h2>Chapter</h2>
                        <div>chapter = {chapter.name}</div>
                        <div>parent legion = {chapter.parentLegion}</div>
                        <div>marines count = {chapter.marinesCount}</div>
                        <div>world = {chapter.world}</div>
                    </div>
                    <div className="main-info">
                        <h2>Main</h2>

                        <div>coordinates - X = {marine.coordinates.x};</div>
                        <div>coordinates - Y = {marine.coordinates.y};</div>
                        <div>creation date = {marine.creationDate}</div>


                    </div>

                    <div className="additional-info">
                        <h2>Additional</h2>
                        <div>category = {marine.category}</div>
                        <div>health = {marine.health}</div>
                        <div>melee weapon = {marine.meleeWeapon}</div>
                        <div>heart count = {marine.heartCount}</div>
                    </div>
                </div>}

            {error ?
                <div className="little-error-message">
                    {error}
                </div>
                : ""}

        </div>
    );
}

export default SpaceMarine;
