import {Chapter} from "../model/Chapter";
import {RawMarineEntity} from "../api/utils/RawMarineEntity";
import {ISpaceMarine} from "../model/ISpaceMarine";
import {xml2json} from "xml-js";

export function handleXml(text: string): Array<ISpaceMarine> {
    let convertedJson = xml2json(text, {compact: true});
    convertedJson = convertedJson.replace(/_/g, "");
    const rawMarinesMap = JSON.parse(convertedJson).SpaceMarineCollection;
    return parseMarineCollection(rawMarinesMap, text);
}

function parseMarineCollection(rawMarinesMap: any, text: string): Array<ISpaceMarine> {
    if (Object.keys(rawMarinesMap).length === 0) {
        return [];
    }

    let parsedMarines = rawMarinesMap.SpaceMarine;
    if (!Array.isArray(parsedMarines)) {
        parsedMarines = [parsedMarines];
    }

    let i = 0;
    return parsedMarines.map(
        (rawMarineEntity: RawMarineEntity): ISpaceMarine => {
            const marine = parseMarine(rawMarineEntity);
            const xmlText = getSpaceMarineXMLById(text, i);
            i++;

            marine.xmlText = xmlText;
            return marine;
        }
    );
}

function parseChapter(chapter: any): Chapter {
    const name: string = chapter && chapter.name ? chapter.name.text : "";
    const parentLegion = chapter && chapter.parentLegion ? chapter.parentLegion.text : "";
    const marinesCount = chapter && chapter.marinesCount ? chapter.marinesCount.text : "";
    const world = chapter && chapter.world ? chapter.world.text : "";
    return {
        name: name,
        marinesCount: marinesCount,
        parentLegion: parentLegion,
        world: world
    }
}

function parseMarine(rawMarineEntity: RawMarineEntity) {
    let chapter: Chapter | null = null;
    if (rawMarineEntity.chapter) {
        chapter = parseChapter(rawMarineEntity.chapter)
    }
    const id = rawMarineEntity.id.text;
    const category = rawMarineEntity.category.text;
    const coordinates = {
        x: rawMarineEntity.coordinates.x.text,
        y: rawMarineEntity.coordinates.y.text
    };
    const creationDate = rawMarineEntity.creationDate ? rawMarineEntity.creationDate.text : "";
    const health = rawMarineEntity.health ? Number(rawMarineEntity.health.text) : null;
    const meleeWeapon = rawMarineEntity.meleeWeapon ? rawMarineEntity.meleeWeapon.text : "";
    const heartCount = rawMarineEntity.heartCount ? Number(rawMarineEntity.heartCount.text) : null;
    const name = rawMarineEntity.name ? rawMarineEntity.name.text : "";

    return {
        category: category,
        chapter: chapter,
        coordinates: coordinates,
        creationDate: creationDate,
        health: health,
        heartCount: heartCount,
        id: id,
        meleeWeapon: meleeWeapon,
        name: name,
        xmlText: ""
    }
}


function getSpaceMarineXMLById(xmlText: string, id: number) {
    const domParser = new DOMParser();
    const dom = domParser.parseFromString(xmlText, "application/xml");

    const node = <HTMLScriptElement>dom.childNodes[0];
    const elements = node.getElementsByTagName("SpaceMarine");

    return elements[id].outerHTML;
}
