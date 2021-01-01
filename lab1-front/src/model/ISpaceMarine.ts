import {Chapter} from "./Chapter";

export interface ISpaceMarine {
    id: number;
    name: string;

    coordinates: { x: number | null; y: number };
    creationDate: string;

    chapter: Chapter | null;
    health: number | null;
    heartCount: number | null;
    category: string | null;
    meleeWeapon: string | null;

    xmlText: string;
}
