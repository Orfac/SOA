import Config from '../api/Config.json';
import { ApiResponse } from './api-response';

export async function deleteById(id: number): Promise<Response> {
    const url = Config.Url + "/marines" + "/" + id;
    return await fetch(url, {
        method: "DELETE"
    });
}

export async function getMarines(finalUrl: string): Promise<Response> {
    return await fetch(finalUrl, {
        method: 'GET',
    })
}

export async function updateById(id: number, text: string): Promise<Response> {
    const url = `${Config.Url}/marines/${id}`;
    return await fetch(url, {
        method: "PUT",
        body: text,
        headers: {
            "Content-Type": "application/xml"
        }
    });
}

export async function save(text: string): Promise<ApiResponse<void>> {
    const url = `${Config.Url}/marines`;
    const response = await fetch(url, {
        method: "POST",
        body: text,
        headers: {
            "Content-Type": "application/xml"
        }
    });

    let errorMessage = "";
    if (response.status !== 201){
        errorMessage = await response.text();
    }
    
    return {
        errorMessage:errorMessage,
        result: null,
        status: response.status
    };
}
