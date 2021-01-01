import Config from '../api/Config.json';

export async function createShip(id: number, name: string) {
    let url = `${Config.Extra}/create/${id}/${name}`;
    return await fetch(url, {
        method: "POST"
    });
}

export async function loadNewMarine(id : number, marineId : number) {
    let url = `${Config.Extra}/${id}/load/${marineId}`;
    return await fetch(url, {
        method: "POST"
    });
}