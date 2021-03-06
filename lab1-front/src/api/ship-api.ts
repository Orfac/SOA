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

    try {
        const response = await fetch(url, {
            method: "GET"
        })

        const responseText = await response.text();
        console.log(responseText);

        let errorMessage = "";
        let result: null | Array<IShip> = null;

        if (response.status !== 200) {
            errorMessage = responseText;
        } else {
            result = [];
            const tmp = JSON.parse(responseText);
            tmp.forEach((value: string) => result?.push(JSON.parse(value)));

        }
        return {
            status: response.status,
            errorMessage: errorMessage,
            result: result
        }
    } catch (e) {
        return {
            status: 500,
            errorMessage: "Cannot get data from server",
            result: null
        };
    }

}

export async function loadNewMarine(id: number, marineId: number): Promise<ApiResponse<void>> {
    const url = `${Config.Extra}/${id}/load/${marineId}`;
    try {
        const response = await fetch(url, {
            method: "POST"
        });
        const errorMessage = response.status !== 200 ? await response.text() : "";
        console.log(errorMessage);
        return {
            status: response.status,
            errorMessage: errorMessage,
            result: null
        };
    } catch (e){
        return {
            status: 500,
            errorMessage: "Cannot get data from server",
            result: null
        };
    }


}
