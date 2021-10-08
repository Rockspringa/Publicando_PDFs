export class ClickleableOption {
    private _title: string;
    private _link: string;

    constructor(title: string, link: string) {
        this._title = title;
        this._link = link;
    }

    get title() { return this._title; }
    get link() { return this._link; }
    
    set title(title: string) { this._title = title; }
    set link(link: string) { this._link = link; }

}