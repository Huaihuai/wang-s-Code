//
//  MyCustomDialog.h
//  pinkture
//
//  Created by nemo on 12-11-6.
//
//

#import <Foundation/Foundation.h>

@interface MyCustomDialog : UIView<UITextViewDelegate, UITextFieldDelegate>
{
    UIView      *panelView;
    UITextView  *contentTextView;
    UIImageView *textViewBg;
    UIButton    *nextButton;
    UIButton    *backButton;
    UILabel     *wordCountLabel;
    UIButton    *clearTextButton;
    
//frame's tect define , for difference screen size
    bool isIphone5;
    CGRect m_screenRect;
    CGRect m_panelRect;
    CGRect m_contentTextRect;
    CGRect m_contentTextBgRect;
    CGRect m_backBtnRect;
    CGRect m_nextBtnRect;
    CGRect m_clearTextBtnRect;
    CGRect m_wordCountLabelRect;
    
    

//control param
    BOOL textHolderSeted;
    BOOL isKeyboardShowing;
    NSString* m_textPlaceHolder;
    UIInterfaceOrientation previousOrientation;
    
    BOOL textEditEventSent;
    
//result param
    int btnResult;
}


-(void)setTextPlaceHolder:(NSString*)tPlaceHolder;
- (void)show:(BOOL)animated;
- (void)hide:(BOOL)animated;

- (NSString*)getDialogResult;
- (int)getDialogBtnResult;  //1: back button clicked. 2: next button clicked

@end
