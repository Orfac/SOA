import Config from '../api/Config.json';
import {ApiResponse} from './api-response';
import {IShip} from "../model/IShip";

export async function createShip(id: number, name: string): Promise<ApiResponse<void>> {
    const url = `${Config.Extra}/create/${id}/${name}`;
    const response = await fetch(url, {
        method: "POST"
    });

    const errorMessage = response.status !== 200 ? await response.text() : "";

    return {
        status: response.status,
        errorMessage: errorMessage,
        result: null
    };
}

export async function getShips(): Promise<ApiResponse<Array<IShip>>> {
    const url = `${Config.Extra}/get`;

    const response = await fetch(url, {
        method: "GET"
    })

    const responseText = await response.text();

    let errorMessage = "";
    let result = null;

    if (response.status !== 200) {
        errorMessage = responseText;
    } else {
        result = JSON.parse(responseText);
    }
    return {
        status: response.status,
        errorMessage: errorMessage,
        result: result
    }

}

export async function loadNewMarine(id: number, marineId: number) : Promise<ApiResponse<void>> {
    const url = `${Config.Extra}/${id}/load/${marineId}`;
    const response =  await fetch(url, {
        method: "POST"
    });
    const errorMessage = response.status !== 200 ? await response.text() : "";

    return {
        status: response.status,
        errorMessage: errorMessage,
        result: null
    };
}
