import { ClickleableOption } from './../html/option.model';
import { UserType } from "./user-type.model";
import { Usuario } from "../usuario.model";

export class Suscriptor extends Usuario {

    private _tags: string[] = [];

    private readonly header = [
        new ClickleableOption('Mis Suscripciones', '/suscriptor/suscripciones'),
        new ClickleableOption('Buscar Revistas', '/user/buscar')
    ];

    private readonly menu = [
        new ClickleableOption('Mi perfil', 'profile'),
        new ClickleableOption('Cerrar sesion', '/login')
    ];

    get type() { return UserType.SUSCRIPTOR; }

    get headerOptions(): ClickleableOption[] {
        return this.header;
    }
    
    get menuOptions(): ClickleableOption[] {
        return this.menu;
    }

    get tags(): string[] { return this._tags; }

    set tags(tags: string[]) { this._tags = tags; }

}