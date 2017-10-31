package gmarket.itheima.cn.gmarket.model.net;

import gmarket.itheima.cn.gmarket.views.ContentPage;

/**
 * Created by asus on 2017/2/5.
 */

//枚举访问网络结果的状态
public enum ResultState {
    LOAD_SUCCESS(ContentPage.STATE_SUCCESS),
    LOAD_ERROR(ContentPage.STATE_ERROR),
    LOAD_EMPTY(ContentPage.STATE_EMPTY);
    private int state;
    private ResultState(int state){
        this.state=state;
    }
    public int getState(){
        return state;
    }


    /*    public class  ResultState{
        ResultState LOAD_SUCCESS=new ResultState(STATE_SUCCESS);
        ResultState LOAD_ERROR=new ResultState(STATE_ERROR);
        ResultState LOAD_EMPTY=new ResultState(STATE_EMPTY);

        private int state;
        private  ResultState(int state){
            this.state=state;
        }
        public int getState(){
            return state;
        }
    }*/
}
