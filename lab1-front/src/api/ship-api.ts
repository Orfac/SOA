import Config from '../api/Config.json';
import { ApiResponse } from './api-response';

export async function createShip(id: number, name: string) : Promise<ApiResponse<void>> {
    const url = `${Config.Extra}/create/${id}/${name}`;
    const response =  await fetch(url, {
        method: "POST"
    });

    const errorMessage = response.status !== 200 ? await response.text() : "";

    const apiResponse : ApiResponse<void> = {
        status: response.status,
        errorMessage: errorMessage,
        result: null
    };
    return apiResponse ;
}

export async function loadNewMarine(id : number, marineId : number) {
    const url = `${Config.Extra}/${id}/load/${marineId}`;
    return await fetch(url, {
        method: "POST"
    });
}