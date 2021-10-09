import { UserType } from "./user-type.model";
import { Usuario } from "../usuario.model";
import { ClickleableOption } from "../html/option.model";

export class Editor extends Usuario {

    private _revistas: string[] = [];

    private readonly header = [
        new ClickleableOption('Mis Suscripciones', 'suscripciones'),
        new ClickleableOption('Buscar Revistas', 'buscar')
    ];

    private readonly menu = [
        new ClickleableOption('Mi perfil', 'profile'),
        new ClickleableOption('Cerrar sesion', '/login')
    ];

    get type() { return UserType.EDITOR; }

    get headerOptions(): ClickleableOption[] {
        return this.header;
    }
    
    get menuOptions(): ClickleableOption[] {
        return this.menu;
    }

    get revistas(): string[] { return this._revistas; }

    set revistas(revistas: string[]) { this._revistas = revistas; }

}